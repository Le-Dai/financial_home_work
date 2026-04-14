package com.example.financial.home.work.dto;

public class ChatResponse {
    private String reply;
    private boolean success;
    private String error;

    public ChatResponse(String reply) {
        this.reply = reply;
        this.success = true;
    }

    public ChatResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public String getReply() { return reply; }
    public boolean isSuccess() { return success; }
    public String getError() { return error; }
}
