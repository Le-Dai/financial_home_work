package com.example.financial.home.work.service;

import com.example.financial.home.work.controller.RagController;
import com.example.financial.home.work.dto.RagRetrieveRequest;
import com.example.financial.home.work.entity.User;
import com.example.financial.home.work.oilGold.entity.OilGold;
import com.example.financial.home.work.oilGold.service.OilGoldService;
import com.example.financial.home.work.repository.UserRepository;
import com.example.financial.home.work.response.ApiResponse;
import com.example.financial.home.work.response.RagRetrieveData;
import dev.ai4j.openai4j.Json;
import dev.langchain4j.agent.tool.Tool;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RagTool {
    @Resource
    private RagController ragController;

    @Resource
    private UserService userService;

    @Resource
    private OilGoldService oilGoldService;

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

    @Tool("""
            查询原油、黄金相关数据（包括价格、日期、涨跌幅、成交量等）
            参数：ask 用户的问题（如：今日金价、原油价格、历史走势等）
            返回：json格式的原油黄金数据列表
            """)
    public String queryOilGold(String ask) {
        // 1. 查询全部数据（你也可以写条件查询）
        List<OilGold> list = oilGoldService.list();

        // 2. 转 JSON 字符串（适合大模型理解）
        return list.stream()
                .map(Json::toJson)
                .collect(Collectors.joining("\n"));
    }
}
