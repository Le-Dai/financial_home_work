package com.example.financial.home.work.dto;

import lombok.Data;
import java.util.Map;

@Data
public class RagRetrieveRequest {
    // 必填：已改写完成的问题
    private String query;

    // 必填：业务会话ID
    private String session_id;

    // 可选，默认5，最大20
    private Integer top_k = 5;

    // 可选：过滤条件
    private Map<String, Object> filters;
}