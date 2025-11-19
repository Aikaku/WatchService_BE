package com.watchserviceagent.watchservice_agent.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * 클래스 이름 : SessionIdManager
 * 기능 : 사용자(에이전트)별 고유 UUID(Session ID)를 생성하고 관리한다.
 * 작성 날짜 : 2025/11/17
 * 작성자 : 이상혁
 */
@Component
@Slf4j
public class SessionIdManager {

    private static final String CONFIG_PATH = "./config/session_id.txt";
    private String sessionId;

    public SessionIdManager() {
        this.sessionId = loadOrCreateSessionId();
    }

    /**
     * UUID 생성 or 기존 파일에서 읽기
     */
    private String loadOrCreateSessionId() {
        try {
            Path path = Paths.get(CONFIG_PATH);

            // config 디렉토리 없으면 생성
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // session_id.txt가 있으면 읽고, 없으면 새로 생성
            if (Files.exists(path)) {
                String existingId = Files.readString(path).trim();
                log.info("[SessionIdManager] 기존 Session ID: {}", existingId);
                return existingId;
            } else {
                String newId = UUID.randomUUID().toString();
                Files.writeString(path, newId, StandardOpenOption.CREATE);
                log.info("[SessionIdManager] 새 Session ID 생성: {}", newId);
                return newId;
            }

        } catch (IOException e) {
            log.error("[SessionIdManager] Session ID 로드 실패", e);
            // 실패 시 임시 UUID라도 리턴
            return UUID.randomUUID().toString();
        }
    }

    /**
     * Session ID 조회
     */
    public String getSessionId() {
        return sessionId;
    }
}
