package com.watchserviceagent.watchservice_agent.collector;

import com.watchserviceagent.watchservice_agent.collector.business.EntropyAnalyzer;
import com.watchserviceagent.watchservice_agent.collector.business.HashCalculator;
import com.watchserviceagent.watchservice_agent.collector.domain.FileData;
import com.watchserviceagent.watchservice_agent.collector.dto.FileAnalysisResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Collector 도메인의 상위 서비스
 * - 파일 경로를 입력받아 Hash, Entropy 등 지표를 종합 분석
 * - 분석 결과를 FileAnalysisResult로 반환
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileCollectorService {

    private final HashCalculator hashCalculator;
    private final EntropyAnalyzer entropyAnalyzer;

    /**
     * 파일을 분석하고 결과를 반환한다.
     * @param filePath 분석 대상 파일의 경로
     * @return FileAnalysisResult (Collector 결과)
     */
    public FileAnalysisResult collect(String filePath) {
        // TODO: FileData 수집 → Hash/Entropy 분석 → 결과 통합
        return null;
    }
}
