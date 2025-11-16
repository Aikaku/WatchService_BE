package com.watchserviceagent.watchservice_agent.ai.domain;

import lombok.Data;

/**
 * AI 분석 결과를 도메인 객체로 표현
 * - 내부적으로 위험 점수, 판정 결과 등을 저장
 */
@Data
public class AiResult {
    private double riskScore;     // AI가 계산한 위험도 점수 (0~1)
    private String verdict;       // 예: SAFE / SUSPICIOUS / MALICIOUS
    private String analyzedAt;    // 분석 완료 시각
}
