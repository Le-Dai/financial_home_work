package com.example.financial.home.work.controller;

import com.example.financial.home.work.dto.ChatRequest;
import com.example.financial.home.work.dto.ChatResponse;
import com.example.financial.home.work.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        if (request.getMessage() == null || request.getMessage().isBlank()) {
            return ResponseEntity.badRequest().body(new ChatResponse(false, "消息不能为空"));
        }
        ChatResponse response = chatService.chat(request);
        return ResponseEntity.ok(response);
    }
}
