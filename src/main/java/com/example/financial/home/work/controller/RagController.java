package com.example.financial.home.work.controller;

import com.example.financial.home.work.dto.RagRetrieveRequest;
import com.example.financial.home.work.response.ApiResponse;
import com.example.financial.home.work.response.RagRetrieveData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/rag")
@RequiredArgsConstructor
public class RagController {

    @Autowired
    private final RestTemplate restTemplate;

    // 从配置读取，不要硬编码
    // @Value("${rag.service.url:http://localhost:8000}")
    private String ragBaseUrl = "http://localhost:8000";

    /**
     * 核心检索接口：混合召回 + 重排
     */
    @PostMapping("/retrieve")
    public ResponseEntity<ApiResponse<RagRetrieveData>> retrieve(
            @RequestHeader(name = "X-Request-Id", required = false) String requestId,
            @RequestBody RagRetrieveRequest request) {

        // 1. 生成/透传 traceId
        String traceId = (requestId == null || requestId.isBlank())
                ? UUID.randomUUID().toString().replace("-", "")
                : requestId;

        // 2. 构造请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Request-Id", traceId);

        HttpEntity<RagRetrieveRequest> entity = new HttpEntity<>(request, headers);

        try {
            // 3. 调用 Python RAG 服务
            String url = ragBaseUrl + "/api/v1/rag/retrieve";
            ApiResponse<RagRetrieveData> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<ApiResponse<RagRetrieveData>>() {
                    }
            ).getBody();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("RAG检索异常 requestId:{}", traceId, e);

            // 4. 按文档返回 500 结构
            ApiResponse<RagRetrieveData> error = ApiResponse.<RagRetrieveData>builder()
                    .code(500)
                    .msg("RAG服务内部错误：" + e.getMessage())
                    .data(null)
                    .requestId(traceId)
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}