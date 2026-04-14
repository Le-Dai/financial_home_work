package com.example.financial.home.work.dto;

public class ChatRequest {
    private String message;
    private java.util.List<MessageHistory> history;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public java.util.List<MessageHistory> getHistory() { return history; }
    public void setHistory(java.util.List<MessageHistory> history) { this.history = history; }

    public static class MessageHistory {
        private String role;
        private String content;

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}
