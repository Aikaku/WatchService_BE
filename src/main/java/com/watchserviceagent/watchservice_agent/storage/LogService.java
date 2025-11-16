package com.watchserviceagent.watchservice_agent.storage;

import com.watchserviceagent.watchservice_agent.collector.dto.FileAnalysisResult;
import com.watchserviceagent.watchservice_agent.storage.domain.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * 로그 저장 및 조회를 담당하는 서비스 클래스
 * - Collector 결과를 DB에 저장하고, 필요 시 조회 기능 제공
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    /**
     * Collector 결과를 DB에 저장
     */
    public void save(FileAnalysisResult result) {
        // TODO: FileAnalysisResult → Log 엔티티 변환 및 저장
    }

    /**
     * 모든 로그 조회
     */
    public List<Log> getAllLogs() {
        // TODO: 로그 전체 조회 로직
        return List.of();
    }

    /**
     * 로그 삭제
     */
    public void deleteLog(Long id) {
        // TODO: ID 기반 로그 삭제
    }
}
