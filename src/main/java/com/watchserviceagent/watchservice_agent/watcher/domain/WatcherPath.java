package com.watchserviceagent.watchservice_agent.watcher.domain;

import java.nio.file.Path;
import lombok.Builder;
import lombok.Data;

/**
 * 감시 대상 폴더의 경로를 관리하는 도메인 모델
 */
@Data
@Builder
public class WatcherPath {

    private Path path; // 감시 대상 경로

    public boolean exists() {
        // TODO: 경로 존재 여부 확인
        return false;
    }

    public boolean isValid() {
        // TODO: 경로 유효성 검사 로직
        return false;
    }
}
