package com.ussp.pojo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ussp.handler.MapJsonTypeHandler;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class UserPlan {
    private Long id;
    private Long userId;
    private Long templateId;
    private Map<String, Integer> userScore;
    private String generatedPlan;
    private String direction;
    private LocalDateTime createTime;
}
