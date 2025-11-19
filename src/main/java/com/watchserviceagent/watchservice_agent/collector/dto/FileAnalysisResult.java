package com.watchserviceagent.watchservice_agent.collector.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Collector의 분석 결과 DTO
 * - Storage 및 AI 도메인으로 전달되는 데이터 객체
 */
@Data
@AllArgsConstructor
public class FileAnalysisResult {
    private String ownerKey;     // 세션 단위 사용자 구분
    private String filePath;
    private long size;
    private String extension;
    private long lastModified;
    private String hash;
    private double entropy;
}
