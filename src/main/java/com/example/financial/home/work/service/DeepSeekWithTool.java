package com.example.financial.home.work.service;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// 2. 定义 AI 服务
@SystemMessage("""
        如果需要查询 XXXX XXXX ,请**必须使用工具**回答。
        
        你是一位专业的金融投资助手，擅长解答关于股票、基金、债券、ETF、宏观经济、个人理财与资产配置等方面的问题。
        请用简洁、专业且易于理解的中文回答用户问题。在适当时候提醒用户：投资有风险，相关内容仅供参考，不构成投资建议。
        """)
interface Assistant {
    String chat(@UserMessage String msg);
}

@Component
public class DeepSeekWithTool {
    @Autowired
    private RagTool ragTool;


    public String chat(String msg) {
        // 构建 DeepSeek 模型
        ChatLanguageModel model = OpenAiChatModel.builder()
                .baseUrl("https://api.deepseek.com/v1")
                .apiKey("sk-086a719bd54b46b9bd0c3dd5ca1ef39d")
                .modelName("deepseek-chat")
                .build();

        // 绑定工具与模型
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .tools(ragTool) // 注册自定义工具
                .build();

        // 自动调用工具
        String result = assistant.chat(msg);
        return result;
    }
}