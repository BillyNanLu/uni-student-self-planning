package com.ussp.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String link;
    private Integer status;
    private LocalDateTime createTime;

    // 额外给前端使用（不入库）
    @JsonProperty("direction_id")
    private Integer directionId;
    private String directionName;
}