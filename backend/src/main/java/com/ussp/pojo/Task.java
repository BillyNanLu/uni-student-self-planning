package com.ussp.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;

    private Long userId;

    private String category;

    private String content;

    private Integer priority;

    private LocalDate dueDate;

    private LocalDateTime completeTime;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDelete;
}
