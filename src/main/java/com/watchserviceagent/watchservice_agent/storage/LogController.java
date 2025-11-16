package com.watchserviceagent.watchservice_agent.storage;

import com.watchserviceagent.watchservice_agent.storage.domain.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 로그 관리용 REST 컨트롤러
 * - 로그 조회, 삭제, 통계용 API 제공
 */
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    /**
     * 전체 로그 조회
     */
    @GetMapping
    public List<Log> getAllLogs() {
        // TODO: logService 호출
        return logService.getAllLogs();
    }

    /**
     * 특정 로그 삭제
     */
    @DeleteMapping("/{id}")
    public String deleteLog(@PathVariable Long id) {
        // TODO: logService 호출
        return "Deleted log ID: " + id;
    }
}
