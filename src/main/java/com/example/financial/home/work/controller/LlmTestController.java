package com.example.financial.home.work.controller;

import com.example.financial.home.work.entity.ChatRecord;
import com.example.financial.home.work.service.LlmService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class LlmTestController {

    @Resource
    LlmService llmService;

    // ======================
    // 接口2：文本向量化测试
    // ======================
    @RequestMapping("/embedding")
    public Map<String, Object> embedding(String text) {
        List<Double> vector = llmService.embedding(text);
        return Map.of(
                "code", 200,
                "msg", "success",
                "vectorSize", vector.size(),
                "vector", vector
        );
    }

    // ======================
    // 接口3：对话生成测试
    // ======================
    @PostMapping("/generate")
    public Map<String, Object> generateAnswer(@RequestBody Map<String, String> req) {
        String prompt = req.get("prompt");
        String answer = llmService.generateAnswer(prompt);
        return Map.of(
                "code", 200,
                "msg", "success",
                "answer", answer
        );
    }

    // ======================
    // 接口4：上下文语义增强测试
    // ======================
    @PostMapping("/context-enhance")
    public Map<String, Object> contextEnhance(@RequestBody Map<String, Object> req) {
        String question = (String) req.get("question");
        // 🔥 绝对安全的接收方式（不会报任何强转错误）
        List<ChatRecord> history = new ArrayList<>();
        List<Map<String, Object>> historyMaps = (List<Map<String, Object>>) req.get("history");
        for (Map<String, Object> map : historyMaps) {
            ChatRecord record = new ChatRecord();
            record.setQuestion((String) map.get("question"));
            record.setAnswer((String) map.get("answer"));
            history.add(record);
        }
        String enhanced = llmService.contextEnhance(question, history);
        return Map.of(
                "code", 200,
                "msg", "success",
                "originalQuestion", question,
                "enhancedQuestion", enhanced
        );
    }
}
