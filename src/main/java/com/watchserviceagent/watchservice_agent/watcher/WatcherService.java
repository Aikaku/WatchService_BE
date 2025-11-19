package com.watchserviceagent.watchservice_agent.watcher;

import com.watchserviceagent.watchservice_agent.collector.FileCollectorService;
import com.watchserviceagent.watchservice_agent.common.util.SessionIdManager;
import com.watchserviceagent.watchservice_agent.watcher.dto.WatcherEventRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * 클래스 이름 : WatcherService
 * 기능 : 지정된 폴더 및 하위 폴더를 감시하고,
 *        Collector와 연동하여 초기 스냅샷 수집 및 변경 이벤트 감지를 수행한다.
 * 작성 날짜 : 2025/11/17
 * 작성자 : 이상혁
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WatcherService {

    private final SessionIdManager sessionIdManager;      // 사용자별 UUID 세션 관리
    private final FileCollectorService collectorService;  // Collector 서비스 연동

    private ExecutorService executor;   // 감시 전용 스레드 풀
    private WatchService watchService;  // Java NIO WatchService
    private Path watchPath;             // 감시 대상 경로

    /**
     * 감시 시작
     * - UUID(Session ID) 불러오기
     * - Collector 초기 스냅샷 실행
     * - 하위 폴더 감시 등록 및 감시 루프 시작
     */
    public void startWatching(String folderPath) throws IOException {
        if (executor != null && !executor.isShutdown()) {
            log.warn("[Watcher] 이미 감시 중입니다: {}", folderPath);
            return;
        }

        this.watchPath = Paths.get(folderPath);
        this.watchService = FileSystems.getDefault().newWatchService();
        this.executor = Executors.newSingleThreadExecutor();

        // 1️⃣ 세션 키(UUID) 로드
        String ownerKey = sessionIdManager.getSessionId();
        log.info("[Watcher] Session ID 로드 완료: {}", ownerKey);

        // 2️⃣ Collector 초기 스냅샷 트리거
        triggerInitialSnapshot(ownerKey, folderPath);

        // 3️⃣ 하위 폴더 재귀 등록
        registerAll(watchPath);

        // 4️⃣ 감시 루프 시작
        startWatchLoop(ownerKey);
    }

    /**
     * Collector 초기 스냅샷 수집 실행
     */
    private void triggerInitialSnapshot(String ownerKey, String folderPath) {
        collectorService.collectAllInPath(ownerKey, folderPath);
        log.info("[Watcher] 초기 스냅샷 수집 완료: {}", folderPath);
    }

    /**
     * 하위 폴더까지 감시 등록
     */
    private void registerAll(Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
                log.info("[Watcher] 감시 등록: {}", dir.toAbsolutePath());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * 감시 루프 시작
     * - Blocking 방식으로 파일 변경 이벤트를 감지
     * - 이벤트 발생 시 Collector에 전달
     */
    private void startWatchLoop(String ownerKey) {
        executor.submit(() -> {
            log.info("[Watcher] 감시 루프 시작: {}", watchPath);
            try {
                while (true) {
                    WatchKey key = watchService.take(); // Blocking (이벤트 대기)
                    Path dir = (Path) key.watchable();

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        // 이벤트 종류 파악
                        if (kind == OVERFLOW) continue;
                        Path changed = dir.resolve((Path) event.context());

                        // DTO 생성
                        WatcherEventRecord record = new WatcherEventRecord(
                                ownerKey,
                                kind.name().replace("ENTRY_", ""),
                                changed.toString(),
                                Instant.now()
                        );

                        // Collector 호출
                        processEvent(record);
                    }

                    if (!key.reset()) {
                        log.warn("[Watcher] 감시 키 유효하지 않음. 종료");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("[Watcher] 감시 스레드 인터럽트 발생");
            } catch (ClosedWatchServiceException e) {
                log.info("[Watcher] WatchService 종료");
            }
        });
    }

    /**
     * 이벤트 처리 → Collector로 파일 분석 요청
     */
    private void processEvent(WatcherEventRecord record) {
        log.info("[Watcher] 이벤트 감지: {}", record);
        try {
            collectorService.collect(record.getOwnerKey(), record.getPath());
        } catch (Exception e) {
            log.error("[Watcher] Collector 호출 중 오류 발생: {}", e.getMessage());
        }
    }

    /**
     * 감시 중지 및 자원 해제
     */
    public void stopWatching() throws IOException {
        if (watchService != null) watchService.close();
        if (executor != null) executor.shutdownNow();
        log.info("[Watcher] 감시 중지 완료: {}", watchPath != null ? watchPath.toAbsolutePath() : "unknown");
    }
}
