package com.ussp.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlanTemplate {
    private Long id;
    private String direction;
    private String templateName;
    // evaluation_rules 存 JSON，使用 fastjson 的 JSONObject 来接收
    // private JSONObject evaluationRules;
    private String evaluationRules;
    private Integer version;
    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
