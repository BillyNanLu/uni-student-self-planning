package com.ussp.dto;

import lombok.Data;

@Data
public class PlanTemplateUpdateDTO {
    private Long id;
    private String templateName;
    private Integer version;
    private Integer status;
    private String evaluationRules; // JSON字符串
}