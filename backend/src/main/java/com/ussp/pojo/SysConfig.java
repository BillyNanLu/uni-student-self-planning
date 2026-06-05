package com.ussp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig {
    private Long id;
    private String configKey;
    private String configValue;
    private String configDesc;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
