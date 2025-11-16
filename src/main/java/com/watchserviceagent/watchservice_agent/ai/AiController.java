package com.watchserviceagent.watchservice_agent.ai;

import com.watchserviceagent.watchservice_agent.ai.dto.AiPayload;
import com.watchserviceagent.watchservice_agent.ai.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * AI 서버 연동용 컨트롤러
 * - Collector에서 분석된 데이터를 AI에 전달하는 요청을 처리
 */
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    /**
     * Collector의 분석 결과를 AI 서버에 전송
     */
    @PostMapping("/analyze")
    public AiResponse analyze(@RequestBody AiPayload payload) {
        // TODO: aiService 호출
        return aiService.requestAnalysis(payload);
    }
}
