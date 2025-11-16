package com.watchserviceagent.watchservice_agent.collector.business;

import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 파일의 Shannon 엔트로피를 계산하는 도메인 서비스
 * - Collector의 핵심 비즈니스 로직 중 하나
 */
@Component
public class EntropyAnalyzer {

    /**
     * 파일의 엔트로피를 계산한다.
     * @param filePath 분석할 파일 경로
     * @return Shannon entropy 값 (0~8)
     */
    public double calculateEntropy(String filePath) throws IOException {
        // TODO: 파일의 바이트 데이터를 읽고 Shannon entropy 계산
        return 0.0;
    }
}
