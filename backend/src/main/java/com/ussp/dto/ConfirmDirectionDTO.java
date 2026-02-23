package com.ussp.dto;

import lombok.Data;

@Data
public class ConfirmDirectionDTO {
    private Long userId;
    private Integer status;
    private String finalDirection;
    private String rejectReason;
}