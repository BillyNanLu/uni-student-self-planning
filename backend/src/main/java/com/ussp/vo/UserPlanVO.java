package com.ussp.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class UserPlanVO {
    private Long id;
    private Long userId;
    private Long templateId;
    private Map<String, Integer> userScore;
    private String generatedPlan;
    private String direction;
    private LocalDateTime createTime;

    // 额外补的字段
    private String username;
    private String templateName;
}
