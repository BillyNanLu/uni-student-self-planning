package com.ussp.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AiChat {
    private Long id;
    private Long userId;
    private String sessionId;
    private Integer role;  // 0=用户, 1=AI
    private String content;
    private LocalDateTime createTime;
}