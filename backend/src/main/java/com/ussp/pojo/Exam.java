package com.ussp.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Exam {
    @NotNull
    private Integer id;
    @NotEmpty
    private String name;
    private String date;
    private String description;
    private Integer directionId;
    private String link;
    private Integer status;
    private LocalDateTime createTime;

    // 额外给前端使用（不入库）
    private String directionName;
}