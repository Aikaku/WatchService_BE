package com.watchserviceagent.watchservice_agent.watcher.domain;

import java.nio.file.Path;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

/**
 * 감시 이벤트를 표현하는 도메인 객체
 */
@Data
@Builder
public class WatcherEvent {

    private String eventType;   // CREATE / MODIFY / DELETE
    private Path filePath;      // 이벤트 발생 경로
    private Instant detectedAt; // 감지 시각

    public boolean isDirectory() {
        // TODO: 폴더 여부 판단 로직
        return false;
    }

    public boolean isFile() {
        // TODO: 파일 여부 판단 로직
        return false;
    }
}
