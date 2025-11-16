package com.watchserviceagent.watchservice_agent.storage;

import com.watchserviceagent.watchservice_agent.storage.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 로그 데이터를 관리하는 Repository
 * - SQLite(DB)에 로그를 저장, 조회, 삭제
 */
@Repository
public class LogRepository {

    // TODO: 필요 시 커스텀 조회 메서드 추가 (예: findByPath, findByEventType 등)
}
