package com.watchserviceagent.watchservice_agent.watcher;

import com.watchserviceagent.watchservice_agent.watcher.dto.WatcherEventRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

/**
 * 감시 서비스의 핵심 로직을 담당하는 Service 클래스
 */
@Service
@Slf4j
public class WatcherService {

    public void startWatching(String folderPath) throws IOException {
        // TODO: WatchService 생성 및 감시 시작
    }

    public void stopWatching() throws IOException {
        // TODO: 감시 중단 및 자원 해제
    }

    private void processEvent(Path filePath, String eventType) {
        // TODO: 이벤트 처리 및 Collector로 전달
        WatcherEventRecord record = new WatcherEventRecord(eventType, filePath.toString(), null);
    }
}
