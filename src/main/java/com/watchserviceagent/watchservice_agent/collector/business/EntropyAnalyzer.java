package com.watchserviceagent.watchservice_agent.collector.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 클래스 이름 : EntropyAnalyzer
 * 기능 : 파일의 Shannon Entropy(정보 무작위도)를 계산한다.
 * 작성 날짜 : 2025/10/01
 * 작성자 : 이상혁
 */
@Component
@Slf4j
public class EntropyAnalyzer {

    /**
     * Shannon Entropy 계산 메서드
     * @param filePath 분석 대상 파일 경로
     * @return 0~8 사이의 엔트로피 값
     */
    public double calculateEntropy(String filePath) {
        try {
            byte[] data = Files.readAllBytes(Path.of(filePath));
            int[] freq = new int[256];

            for (byte b : data) {
                freq[b & 0xFF]++;
            }

            double entropy = 0.0;
            for (int f : freq) {
                if (f > 0) {
                    double p = (double) f / data.length;
                    entropy -= p * (Math.log(p) / Math.log(2)); // Shannon 공식
                }
            }
            return entropy; // 0~8 범위
        } catch (IOException e) {
            log.error("[EntropyAnalyzer] 파일 읽기 실패: {}", filePath, e);
            return 0.0;
        }
    }
}
