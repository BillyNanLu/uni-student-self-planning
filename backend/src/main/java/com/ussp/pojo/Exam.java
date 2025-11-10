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
    private LocalDateTime date;
    private String description;
    private Integer directionId;
    private String link;
    private Integer status;
    private LocalDateTime createTime;
}