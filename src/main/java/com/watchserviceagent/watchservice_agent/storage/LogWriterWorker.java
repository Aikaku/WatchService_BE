package com.watchserviceagent.watchservice_agent.storage;

import com.watchserviceagent.watchservice_agent.collector.dto.FileAnalysisResult;
import com.watchserviceagent.watchservice_agent.storage.domain.Log;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Collector → Storage 구간의 비동기 큐 역할을 하는 워커.
 *
 * 흐름:
 *  - Collector(FileCollectorService)가 FileAnalysisResult를 enqueue(...)
 *  - LogWriterWorker는 별도 스레드에서 queue.take()로 결과를 하나씩 꺼내어
 *    LogRepository.insertLog(log) 를 호출해 SQLite에 순차적으로 저장한다.
 *
 * 장점:
 *  - Collector/Watcher 스레드에서는 DB Lock/지연을 신경 쓰지 않고 빠르게 큐에만 넣고 반환 가능.
 *  - DB 접근은 한 스레드에서 순차적으로 이루어져 락/동시성 문제를 줄인다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogWriterWorker {

    private final LogRepository logRepository;

    /** Collector에서 전달되는 분석 결과를 보관하는 큐 */
    private final BlockingQueue<FileAnalysisResult> queue = new LinkedBlockingQueue<>();

    /** 워커 스레드 참조 */
    private Thread workerThread;

    /** 종료 플래그 */
    private volatile boolean running = true;

    /**
     * 스프링 컨텍스트 초기화 시 워커 스레드 시작.
     */
    @PostConstruct
    public void start() {
        workerThread = new Thread(this::runWorker, "LogWriterWorker-Thread");
        workerThread.start();
        log.info("[LogWriterWorker] 워커 스레드 시작");
    }

    /**
     * 스프링 컨텍스트 종료 시 워커 스레드 종료.
     */
    @PreDestroy
    public void stop() {
        running = false;
        if (workerThread != null && workerThread.isAlive()) {
            workerThread.interrupt();
        }
        log.info("[LogWriterWorker] 워커 스레드 종료 요청");
    }

    /**
     * Collector 쪽에서 FileAnalysisResult를 큐에 넣을 때 사용하는 메서드.
     *
     * @param result Collector가 생성한 분석 결과
     */
    public void enqueue(FileAnalysisResult result) {
        if (result == null) {
            return;
        }
        try {
            queue.put(result); // 큐가 가득 차면 대기 (현재는 무한 큐)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("[LogWriterWorker] enqueue 중 인터럽트 발생", e);
        }
    }

    /**
     * 워커 스레드 본체.
     * - running 플래그가 true인 동안 큐에서 결과를 꺼내어 DB에 저장한다.
     */
    private void runWorker() {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                // 큐에서 다음 결과 꺼내기 (없으면 대기)
                FileAnalysisResult result = queue.take();
                // FileAnalysisResult → Log로 변환 후 저장
                Log logEntity = mapToLog(result);
                logRepository.insertLog(logEntity);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("[LogWriterWorker] 워커 스레드 인터럽트, 종료 준비");
            } catch (Exception e) {
                log.error("[LogWriterWorker] 로그 저장 중 예외 발생", e);
            }
        }
        log.info("[LogWriterWorker] 워커 루프 종료");
    }

    /**
     * FileAnalysisResult DTO를 Log 도메인으로 매핑.
     *
     * @param r Collector 결과
     * @return Log 엔티티
     */
    private Log mapToLog(FileAnalysisResult r) {
        Instant collectedAt = r.getCollectedAt();
        if (collectedAt == null) {
            collectedAt = Instant.now();
        }

        return Log.builder()
                .ownerKey(r.getOwnerKey())
                .eventType(r.getEventType())
                .path(r.getPath())
                .exists(r.isExists())
                .size(r.getSize())
                .lastModifiedTime(r.getLastModifiedTime())
                .hash(r.getHash())
                .entropy(r.getEntropy())
                // AI 결과는 아직 없으므로 null
                .aiLabel(null)
                .aiScore(null)
                .aiDetail(null)
                .collectedAt(collectedAt)
                .build();
    }
}
