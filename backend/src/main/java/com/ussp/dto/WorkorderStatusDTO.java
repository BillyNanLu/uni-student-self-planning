package com.ussp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkorderStatusDTO {
    private String status;        // 必填 resolved
    private LocalDateTime handledAt;
    private String remark;        // 备注
}
