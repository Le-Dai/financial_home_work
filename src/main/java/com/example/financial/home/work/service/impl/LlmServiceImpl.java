package com.example.financial.home.work.service.impl;

import com.example.financial.home.work.entity.ChatRecord;
import com.example.financial.home.work.service.LlmService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlmServiceImpl implements LlmService {

    private final ChatLanguageModel chatLanguageModel;

    @Override
    public String generateAnswer(String prompt) {
        try {
            List<ChatMessage> messages = new ArrayList<>();
            messages.add(UserMessage.from(prompt));
            
            Response<AiMessage> response = chatLanguageModel.generate(messages);
            return response.content().text();
        } catch (Exception e) {
            log.error("大模型调用失败", e);
            throw new RuntimeException("大模型调用失败：" + e.getMessage(), e);
        }
    }

    @Override
    public String contextEnhance(String question, List<ChatRecord> history) {
        try {
            // 1. 拼接历史对话
            StringBuilder historyPrompt = new StringBuilder();
            for (ChatRecord record : history) {
                historyPrompt.append("用户：").append(record.getQuestion()).append("\n");
                historyPrompt.append("助手：").append(record.getAnswer()).append("\n");
            }

            // 2. 构造增强提示词（让大模型补全省略指代）
            String enhancePrompt = """
                    你是一个上下文补全助手。
                    根据历史对话，把用户当前问题里的省略、指代补全成完整、明确、独立的问题。
                    只输出最终补全后的问题，不要解释。
                                        
                    历史对话：
                    %s
                                        
                    当前问题：%s
                    """.formatted(historyPrompt.toString(), question);

            // 3. 直接调用大模型生成
            return generateAnswer(enhancePrompt);

        } catch (Exception e) {
            log.error("上下文增强失败，返回原问题", e);
            return question; // 失败则返回原问题
        }
    }
}