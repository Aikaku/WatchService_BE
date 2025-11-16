package com.watchserviceagent.watchservice_agent.watcher;

import org.springframework.stereotype.Repository;

/**
 * 감시 관련 데이터(설정, 이력 등)를 저장/조회하는 Repository
 */
@Repository
public class WatcherRepository {

    public void savePath(String folderPath) {
        // TODO: 감시 대상 경로 저장
    }

    public String getLastWatchedPath() {
        // TODO: 마지막 감시 경로 조회
        return null;
    }
}
