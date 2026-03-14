package com.ussp.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AiReportRequestDTO {
    private Long userId;

    // 匹配度
    private Map<String, Integer> matchRate;
    private String recommend;
    private String preferredDirection;
    private String finalDirection;

    // 用户画像
    private List<String> interests;
    private List<String> abilities;
    private List<String> selfEvaluation;
}
