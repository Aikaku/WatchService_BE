package com.watchserviceagent.watchservice_agent.watcher;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

/**
 * 감시 시작/중지 등의 사용자 요청을 처리하는 Controller
 */
@RestController
@RequestMapping("/watcher")
@RequiredArgsConstructor
public class WatcherController {

    private final WatcherService watcherService;

    @PostMapping("/start")
    public String startWatching(@RequestParam String folderPath) {
        // TODO: watcherService 호출
        return "Watcher started";
    }

    @PostMapping("/stop")
    public String stopWatching() {
        // TODO: watcherService 중지 호출
        return "Watcher stopped";
    }
}
