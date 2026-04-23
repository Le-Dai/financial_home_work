package com.example.financial.home.work.service;

import com.example.financial.home.work.controller.RagController;
import com.example.financial.home.work.dto.RagRetrieveRequest;
import com.example.financial.home.work.entity.User;
import com.example.financial.home.work.repository.UserRepository;
import com.example.financial.home.work.response.ApiResponse;
import com.example.financial.home.work.response.RagRetrieveData;
import dev.ai4j.openai4j.Json;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RagTool {
    @Autowired
    private RagController ragController;

    @Autowired
    private UserService userService;

    @Tool("""
            查询指定城市天气
            参数：city 城市
            """)
    public String getWeather(String city) {
        return city + "今日晴，25℃ ";
    }

    //todo set defined
    @Tool("""
            查询rag 系统
            参数：ask 用户的问题
            """)
    public String callRag(String ask) {
        RagRetrieveRequest ragRetrieveRequest = new RagRetrieveRequest();
        ragRetrieveRequest.setQuery(ask);
        ragRetrieveRequest.setSession_id(UUID.randomUUID().toString());
        ResponseEntity<ApiResponse<RagRetrieveData>> retrieve = ragController.retrieve(UUID.randomUUID().toString(), ragRetrieveRequest);
        List<String> collect = new ArrayList<>();
        collect = retrieve.getBody()
                .getData()
                .getRetrieved_chunks()
                .stream().map(i -> i.getContent())
                .collect(Collectors.toUnmodifiableList());
        return String.join("", collect);
    }

    @Tool("""
            mysql query
            参数：ask 用户的问题
            """)
    public String queryMysql(String ask) {
        List<String> collect = userService.findAll()
                .stream()
                .map(Json::toJson)
                .collect(Collectors.toUnmodifiableList());
        return String.join("", collect);
    }
}
