package com.watchserviceagent.watchservice_agent.watcher;

import com.watchserviceagent.watchservice_agent.collector.FileCollectorService;
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
 *        이벤트 발생 시 Collector로 전달한다.
 * 작성 날짜 : 2025/11/16
 * 작성자 : 이상혁
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WatcherService {

    private final FileCollectorService collectorService; // Collector 의존성

    private ExecutorService executor;   // 감시 전용 스레드 풀
    private WatchService watchService;  // NIO WatchService
    private Path watchPath;             // 감시 경로

    /**
     * 감시 시작
     * - Collector에게 초기 스냅샷 요청
     * - WatchService로 실시간 감시 시작
     */
    public void startWatching(String folderPath) throws IOException {
        if (executor != null && !executor.isShutdown()) {
            log.warn("[Watcher] 이미 감시 중입니다: {}", folderPath);
            return;
        }

        this.watchPath = Paths.get(folderPath);
        this.watchService = FileSystems.getDefault().newWatchService();
        this.executor = Executors.newSingleThreadExecutor();

        // 1️⃣ Collector 초기 스냅샷 트리거
        triggerInitialSnapshot(folderPath);

        // 2️⃣ 하위 폴더까지 감시 등록
        registerAll(watchPath);

        // 3️⃣ 감시 루프 실행
        startWatchLoop();
    }

    /**
     * Collector 초기 스냅샷 실행
     */
    private void triggerInitialSnapshot(String folderPath) {
        collectorService.collectAllInPath(folderPath);
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
     * 감시 루프 실행
     */
    private void startWatchLoop() {
        executor.submit(() -> {
            log.info("[Watcher] 감시 시작: {}", watchPath);
            try {
                while (true) {
                    WatchKey key = watchService.take(); // Blocking
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changed = ((Path) key.watchable()).resolve((Path) event.context());
                        String eventType = event.kind().name().replace("ENTRY_", "");
                        processEvent(changed, eventType);
                    }
                    if (!key.reset()) break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("[Watcher] 감시 스레드 인터럽트 발생");
            } catch (ClosedWatchServiceException e) {
                log.info("[Watcher] 감시 서비스 종료됨");
            }
        });
    }

    /**
     * 감지된 이벤트를 Collector로 전달
     */
    private void processEvent(Path filePath, String eventType) {
        WatcherEventRecord record = new WatcherEventRecord(
                eventType,
                filePath.toString(),
                Instant.now()
        );
        log.info("[Watcher] 이벤트 감지: {}", record);

        // Collector 재분석 요청
        collectorService.collect(filePath.toString());
    }

    /**
     * 감시 중지
     * - WatchService 종료
     * - ExecutorService 종료
     */
    public void stopWatching() throws IOException {
        if (watchService != null) {
            watchService.close();
        }
        if (executor != null) {
            executor.shutdownNow();
        }
        log.info("[Watcher] 감시 중지 완료: {}", watchPath != null ? watchPath.toAbsolutePath() : "unknown");
    }
}
