package com.watchserviceagent.watchservice_agent.ai;

import com.watchserviceagent.watchservice_agent.ai.dto.AiPayload;
import com.watchserviceagent.watchservice_agent.ai.dto.AiResponse;
import com.watchserviceagent.watchservice_agent.ai.domain.AiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * AI 서버와 통신하는 서비스 클래스
 * - HTTP POST 요청을 통해 Collector 결과를 AI 서버로 전송
 * - AI 응답을 AiResult 및 AiResponse 형태로 반환
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AiService {

    private final RestTemplate restTemplate = new RestTemplate();

    // AI 서버 URL (향후 환경 설정으로 분리 예정)
    private static final String AI_SERVER_URL = "http://localhost:5000/analyze";

    /**
     * Collector 결과를 AI 서버로 전송하고 응답을 받는다.
     */
    public AiResponse requestAnalysis(AiPayload payload) {
        // TODO: restTemplate.postForObject() 사용
        return new AiResponse("success", "AI 응답 테스트");
    }

    /**
     * AI 응답을 도메인 객체로 변환
     */
    private AiResult convertToDomain(AiResponse response) {
        // TODO: 변환 로직
        return new AiResult();
    }
}
