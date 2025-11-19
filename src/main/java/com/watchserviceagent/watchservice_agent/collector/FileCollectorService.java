package com.watchserviceagent.watchservice_agent.collector;

import com.watchserviceagent.watchservice_agent.collector.business.EntropyAnalyzer;
import com.watchserviceagent.watchservice_agent.collector.business.HashCalculator;
import com.watchserviceagent.watchservice_agent.collector.domain.FileData;
import com.watchserviceagent.watchservice_agent.collector.dto.FileAnalysisResult;
import com.watchserviceagent.watchservice_agent.storage.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * Collector 도메인의 상위 서비스
 * - 개별 파일 분석 및 초기 스냅샷 수집 담당
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileCollectorService {

    private final HashCalculator hashCalculator;
    private final EntropyAnalyzer entropyAnalyzer;
    private final LogService logService;

    /**
     * 단일 파일 분석 (기존 로직)
     * @param filePath 분석 대상 파일 경로
     * @return 분석 결과 (해시, 엔트로피 등)
     */
    public FileAnalysisResult collect(String ownerKey, String filePath) {
        // TODO: 기존 단일 파일 분석 로직
        return null;
    }

    /**
     * 초기 스냅샷 수집 메서드
     * - 사용자가 감시 경로 등록 시 실행됨
     * - 폴더 및 하위 모든 파일의 해시/엔트로피를 계산하여 Storage에 저장
     */
    public void collectAllInPath(String owenerKey, String folderPath) {
        // TODO: Files.walk() 사용하여 폴더 내 모든 파일 분석 및 저장
    }

    /**
     * Collector 분석 결과를 Storage에 비교 요청
     * - 이전 상태와 비교하여 변경 여부 판단
     */
    public void compareWithPrevious(FileAnalysisResult newResult) {
        // TODO: LogService를 통해 이전 결과 조회 및 비교 수행
    }
}
