package com.watchserviceagent.watchservice_agent.storage;

import com.watchserviceagent.watchservice_agent.collector.dto.FileAnalysisResult;
import com.watchserviceagent.watchservice_agent.storage.domain.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 로그 저장 및 비교를 담당하는 서비스 클래스
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
        // TODO: FileAnalysisResult → Log 변환 후 저장
    }

    /**
     * 모든 로그 조회
     */
    public List<Log> getAllLogs() {
        // TODO: 전체 로그 조회
        return List.of();
    }

    /**
     * 로그 삭제
     */
    public void deleteLog(Long id) {
        // TODO: 로그 삭제
    }

    /**
     * 특정 파일 경로의 마지막 분석 결과 조회
     * - 변경 비교를 위해 이전 상태 불러옴
     */
    public FileAnalysisResult findLastByPath(String path) {
        // TODO: 최근 로그 조회 로직
        return null;
    }

    /**
     * Collector가 전달한 새로운 분석 결과와 기존 상태 비교
     * - 해시, 엔트로피, 파일 크기 등 비교
     * @return true → 변경됨, false → 동일
     */
    public boolean isChanged(FileAnalysisResult newResult) {
        // TODO: 이전 결과와 비교 후 boolean 반환
        return false;
    }
}
