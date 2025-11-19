package com.watchserviceagent.watchservice_agent.watcher.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Collector로 전달할 이벤트 데이터 DTO
 */
@Data
@AllArgsConstructor
public class WatcherEventRecord {
    private String ownerKey;     // 사용자 세션 키 (UUID)
    private String eventType;    // CREATE / MODIFY / DELETE
    private String path;         // 파일 경로
    private Instant timestamp;   // 이벤트 발생 시각
}

