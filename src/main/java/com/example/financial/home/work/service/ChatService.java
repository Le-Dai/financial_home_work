package com.example.financial.home.work.service;

import com.example.financial.home.work.dto.ChatRequest;
import com.example.financial.home.work.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChatService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.model}")
    private String model;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    private static final String SYSTEM_PROMPT =
        "你是一位专业的金融投资助手，擅长解答关于股票、基金、债券、ETF、宏观经济、个人理财与资产配置等方面的问题。" +
        "请用简洁、专业且易于理解的中文回答用户问题。在适当时候提醒用户：投资有风险，相关内容仅供参考，不构成投资建议。";

    private final RestTemplate restTemplate = new RestTemplate();

    public ChatResponse chat(ChatRequest request) {
        List<Map<String, String>> messages = new ArrayList<>();

        // system message
        messages.add(Map.of("role", "system", "content", SYSTEM_PROMPT));

        // history
        if (request.getHistory() != null) {
            for (ChatRequest.MessageHistory h : request.getHistory()) {
                messages.add(Map.of("role", h.getRole(), "content", h.getContent()));
            }
        }

        // current user message
        messages.add(Map.of("role", "user", "content", request.getMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            Map<?, ?> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("choices")) {
                List<?> choices = (List<?>) responseBody.get("choices");
                if (!choices.isEmpty()) {
                    Map<?, ?> choice = (Map<?, ?>) choices.get(0);
                    Map<?, ?> messageMap = (Map<?, ?>) choice.get("message");
                    String content = (String) messageMap.get("content");
                    return new ChatResponse(content);
                }
            }
            return new ChatResponse("抱歉，未能获取有效回复，请稍后再试。");
        } catch (Exception e) {
            return new ChatResponse("请求失败：" + e.getMessage());
        }
    }
}
