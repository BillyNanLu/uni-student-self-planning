package com.ussp.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PlanningScoreDTO {
    // RIASEC 六维度
    private Map<String, Integer> riasec = new HashMap<>();

    // 能力测评维度
    private Map<String, Integer> ability = new HashMap<>();

    // 自我评价维度
    private Map<String, Integer> selfEvaluation = new HashMap<>();
}
