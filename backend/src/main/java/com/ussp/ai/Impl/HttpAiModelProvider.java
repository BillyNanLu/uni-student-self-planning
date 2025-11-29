package com.ussp.ai.Impl;

import com.ussp.ai.AiModelProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collections;

@Component
public class HttpAiModelProvider implements AiModelProvider {

    @Value("${ai.provider.endpoint:}")
    private String endpoint;

    @Value("${ai.provider.apikey:}")
    private String apiKey;

    @Value("${ai.provider.model:doubao-seed-1-6-251015}")
    private String model;

    private final RestTemplate rest = new RestTemplate();

    @Override
    public String generateText(String prompt) {
        if (endpoint == null || endpoint.isEmpty()) {
            throw new RuntimeException("AI provider endpoint not configured");
        }

        // 豆包API需要完整的URL路径
        String fullUrl = endpoint + "/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // 豆包API的请求格式（Chat Completions格式）
        JSONObject body = new JSONObject();
        body.put("model", model); // 指定豆包模型

        // 构建messages数组
        JSONArray messages = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        body.put("messages", messages);
        body.put("max_tokens", 4000); // 豆包模型支持4000 Token
        body.put("temperature", 0.7);
        body.put("stream", false); // 非流式响应

        HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
        ResponseEntity<String> resp = rest.exchange(fullUrl, HttpMethod.POST, entity, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("AI provider error: " + resp.getStatusCode() + " - " + resp.getBody());
        }

        // 解析豆包API的响应格式
        JSONObject responseJson = JSON.parseObject(resp.getBody());
        String text = null;

        if (responseJson.containsKey("choices") && !responseJson.getJSONArray("choices").isEmpty()) {
            JSONObject choice = responseJson.getJSONArray("choices").getJSONObject(0);
            if (choice.containsKey("message")) {
                text = choice.getJSONObject("message").getString("content");
            }
        }

        // 备用解析逻辑
        if (text == null || text.isEmpty()) {
            text = responseJson.getString("content");
        }
        if (text == null || text.isEmpty()) {
            text = responseJson.toJSONString();
        }

        return text;
    }
}