package com.watchserviceagent.watchservice_agent.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Collector에서 AI 서버로 전달되는 요청 데이터 DTO
 * - 파일 분석 결과를 기반으로 AI 분석 요청을 보냄
 */
@Data
@AllArgsConstructor
public class AiPayload {
    private String filePath;
    private String hash;
    private double entropy;
    private String eventType;
}
