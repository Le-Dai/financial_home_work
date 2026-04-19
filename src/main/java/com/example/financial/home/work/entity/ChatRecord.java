package com.example.financial.home.work.entity;

import lombok.Data;

/**
 * 对话历史记录
 */
@Data
public class ChatRecord {
    /**
     * 历史提问
     */
    private String question;

    /**
     * 历史回答
     */
    private String answer;
}
