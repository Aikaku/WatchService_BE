package com.watchserviceagent.watchservice_agent.collector.domain;

import lombok.Builder;
import lombok.Data;
import java.nio.file.Path;

/**
 * Collector 도메인의 기본 데이터 모델
 * - 분석 대상 파일의 기본 속성 정보를 나타냄
 */
@Data
@Builder
public class FileData {
    private Path filePath;      // 파일 경로
    private long size;          // 파일 크기 (byte 단위)
    private long lastModified;  // 마지막 수정 시각
    private String extension;   // 확장자

    /**
     * 파일 메타데이터 수집 (정적 팩토리 메서드)
     */
    public static FileData fromPath(String filePath) {
        // TODO: Files API를 사용하여 FileData 생성
        return FileData.builder().build();
    }
}
