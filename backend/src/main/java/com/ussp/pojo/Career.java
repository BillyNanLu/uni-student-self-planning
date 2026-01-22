package com.ussp.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Career {
    private Integer id;
    private Integer directionId;
    private String name;
    private String detail;
    private String extraField;
    private String extraType;
    private Integer status;
    private LocalDateTime createTime;

    private String directionName;
}
