package com.watchserviceagent.watchservice_agent.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AI 서버로부터 수신하는 응답 DTO
 * - 분석 결과 상태 및 메시지 또는 위험도 점수를 포함
 */
@Data
@AllArgsConstructor
public class AiResponse {
    private String status;   // success / error
    private String message;  // 응답 메시지 또는 위험도 설명
}
