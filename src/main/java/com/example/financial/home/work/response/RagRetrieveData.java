package com.example.financial.home.work.response;

import lombok.Data;

import java.util.List;

@Data
public class RagRetrieveData {
    private String session_id;
    private List<RetrievedChunk> retrieved_chunks;
}
