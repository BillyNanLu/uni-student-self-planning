package com.ussp.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Further {
    private Integer id;
    private Integer directionId; // 关联的方向ID
    private String title;        // 资源标题
    private String link;         // 资源链接
    private String description;  // 资源描述
    private String type;         // 资源类型（官方渠道/免费资源等）
    private Integer status;      // 状态（1启用，0停用）
    private LocalDateTime createTime; // 创建时间
}
