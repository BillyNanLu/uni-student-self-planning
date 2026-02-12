package com.ussp.vo;

import lombok.Data;

import java.util.Map;

@Data
public class DirectionMatchVO {
    private Map<String, Integer> matchRate;
    private String recommend;
    private String preferredDirection;
    private Integer confirmStatus;
    private String finalDirection;
}
