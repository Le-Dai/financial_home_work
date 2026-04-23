package com.example.financial.home.work.service;

import com.example.financial.home.work.dto.ChatRequest;
import com.example.financial.home.work.dto.ChatResponse;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private static final String SYSTEM_PROMPT =
        "你是一位专业的金融投资助手，擅长解答关于股票、基金、债券、ETF、宏观经济、个人理财与资产配置等方面的问题。" +
        "请用简洁、专业且易于理解的中文回答用户问题。在适当时候提醒用户：投资有风险，相关内容仅供参考，不构成投资建议。";

    private final ChatLanguageModel chatLanguageModel;

    @Autowired
    private DeepSeekWithTool deepSeekWithTool;

    public ChatService(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    public ChatResponse chat(ChatRequest request) {
        List<ChatMessage> messages = new ArrayList<>();

        // system message
        messages.add(SystemMessage.from(SYSTEM_PROMPT));

        // history
        if (request.getHistory() != null) {
            for (ChatRequest.MessageHistory h : request.getHistory()) {
                if ("user".equalsIgnoreCase(h.getRole())) {
                    messages.add(UserMessage.from(h.getContent()));
                } else if ("assistant".equalsIgnoreCase(h.getRole()) || "ai".equalsIgnoreCase(h.getRole())) {
                    messages.add(AiMessage.from(h.getContent()));
                }
            }
        }

        // current user message
        messages.add(UserMessage.from(request.getMessage()));

        try {
            String result = deepSeekWithTool.chat(request.getMessage());
            return new ChatResponse(result);
        } catch (Exception e) {
            return new ChatResponse("请求失败：" + e.getMessage());
        }
    }
}
