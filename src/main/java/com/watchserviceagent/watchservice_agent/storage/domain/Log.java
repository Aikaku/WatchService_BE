package com.watchserviceagent.watchservice_agent.storage.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

/**
 * 저장되는 로그 데이터를 나타내는 엔티티 클래스
 * - Collector 분석 결과를 기반으로 DB에 저장
 */
@Entity
@Table(name = "event_logs")
@Data
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;             // 고유 식별자

    private String ownerKey;      // 세션 식별자 (UUID)
    private String eventType;    // CREATE / MODIFY / DELETE
    private String path;         // 파일 경로
    private String hash;         // 파일 해시값
    private double entropy;      // 엔트로피 값
    private long size;           // 파일 크기
    private String extension;    // 파일 확장자
    private long lastModified;
    private Instant timestamp;   // 로그 저장 시각
}
