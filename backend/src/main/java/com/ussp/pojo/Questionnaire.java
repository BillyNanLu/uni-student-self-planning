package com.ussp.pojo;

import lombok.Data;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Questionnaire {

    private Long id;

    private String title;               // 问卷标题（兴趣测评、能力测评、自评）
    private String description;         // 简介
    private Byte status;                // 状态（1=启用，0=停用）
    private LocalDateTime createTime;   // 创建时间
}
