package com.ussp.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDirection {

    private Long id;        // 主键ID
    private Long userId;    // 用户ID

    private String preferredDirection;  // 用户主动选择方向（问卷第25题）
    private String systemDirection;     // 系统分析推荐方向
    private String finalDirection;      // 最终确认方向（用于AI报告）

    private Integer isConflict;         // 是否不一致（0=一致，1=不一致）
    private Integer confirmStatus;      // 用户是否确认系统建议（0未确认、1采纳系统、2拒绝系统）

    private String aiReason;           // AI推荐方向理由
    private String rejectReason;       // 用户拒绝系统建议的原因

    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}
