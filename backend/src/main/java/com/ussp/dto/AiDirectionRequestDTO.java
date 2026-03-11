package com.ussp.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AiDirectionRequestDTO {
    private Long userId;

    private String preferredDirection;

    // 匹配度
    private Map<String, Integer> matchRate;

    // RIASEC 六维度
    private Map<String, Integer> riasec = new HashMap<>();
    // 能力测评维度
    private Map<String, Integer> ability = new HashMap<>();
    // 自我评价维度（示例，可按需扩展）
    private Map<String, Integer> self = new HashMap<>();

    // 用户画像
    private List<String> interests;
    private List<String> abilities;
    private List<String> selfEvaluation;
}
