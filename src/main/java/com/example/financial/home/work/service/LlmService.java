package com.example.financial.home.work.service;

import com.example.financial.home.work.entity.ChatRecord;

import java.util.List;

public interface LlmService {
    /**
     * 接口2：文本向量化接口
     * 将用户问题转为向量，用于 Milvus 向量检索
     * @param text 向量化文本（融合记忆后的用户提问）
     * @return 一维向量数组 List<Double>
     */
    List<Double> embedding(String text);

    /**
     * 接口3：大模型对话生成接口
     * 输入完整Prompt，返回大模型回答
     * @param prompt 拼接好的完整提示词
     * @return 大模型原始回答
     */
    String generateAnswer(String prompt);

    /**
     * 接口4：上下文语义增强接口（可选）
     * 结合历史对话，补全用户省略的问题（如：它→原油期货）
     * @param question 用户当前提问
     * @param history 历史对话列表
     * @return 补全后的增强问题
     */
    String contextEnhance(String question, List<ChatRecord> history);
}
