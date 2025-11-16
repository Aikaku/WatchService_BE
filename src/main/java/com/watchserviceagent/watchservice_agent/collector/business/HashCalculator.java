package com.watchserviceagent.watchservice_agent.collector.business;

import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 파일의 해시(SHA-256, MD5 등)를 계산하는 도메인 서비스
 * - Collector의 핵심 비즈니스 로직 중 하나
 */
@Component
public class HashCalculator {

    /**
     * SHA-256 해시를 계산한다.
     * @param filePath 분석할 파일 경로
     * @return SHA-256 해시 문자열
     */
    public String calculateSHA256(String filePath) throws IOException {
        // TODO: SHA-256 해시 계산 로직
        return null;
    }

    /**
     * MD5 해시를 계산한다.
     * @param filePath 분석할 파일 경로
     * @return MD5 해시 문자열
     */
    public String calculateMD5(String filePath) throws IOException {
        // TODO: MD5 해시 계산 로직
        return null;
    }
}
