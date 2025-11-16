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
    private String eventType;
    private String path;
    private Instant timestamp;
}
