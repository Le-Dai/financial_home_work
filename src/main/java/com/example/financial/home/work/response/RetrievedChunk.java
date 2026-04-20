package com.example.financial.home.work.response;

import lombok.Data;

import java.util.Map;

@Data
public class RetrievedChunk {
    private String document_id;
    private String content;
    private Double score;
    private Map<String, Object> metadata;
}
