package com.ussp.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Workorder {
    private Long id;
    private String workorderNo;   // 工单编号
    private String type;          // 反馈类型
    private String priority;      // 紧急程度
    private String content;       // 内容
    private String contact;       // 联系方式
    private String status;        // 状态：pending、processing、resolved
    private LocalDateTime createdAt;
    private LocalDateTime handledAt;
    private String remark;
}