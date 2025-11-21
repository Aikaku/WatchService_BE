package com.watchserviceagent.watchservice_agent.storage;

import com.watchserviceagent.watchservice_agent.common.util.SessionIdManager;
import com.watchserviceagent.watchservice_agent.storage.domain.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 로그 조회를 위한 REST 컨트롤러.
 *
 * - /logs/recent : 현재 세션(ownerKey)에 대한 최근 로그 N건 조회
 *   (프론트엔드에서 사용자가 감시 결과를 확인할 때 사용)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;
    private final SessionIdManager sessionIdManager;

    /**
     * 현재 세션(에이전트)의 최근 로그 조회.
     *
     * @param limit 최대 조회 개수 (기본값 100)
     * @return Log 리스트(JSON)
     */
    @GetMapping("/logs/recent")
    public List<Log> getRecentLogs(@RequestParam(defaultValue = "100") int limit) {
        String ownerKey = sessionIdManager.getSessionId();
        log.info("[LogController] 최근 로그 조회 요청 - ownerKey={}, limit={}", ownerKey, limit);
        return logService.getRecentLogs(ownerKey, limit);
    }
}
