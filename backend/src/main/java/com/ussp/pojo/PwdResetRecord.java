package com.ussp.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PwdResetRecord {

    private Long id;

    private String username;

    private String email;

    private String code;

    /**
     * 用户状态：0-非已注册用户，1-已注册用户
     */
    private Integer userStatus;

    /**
     * 重置状态：
     * 0-未使用（非平台用户未发验证码）
     * 1-未使用（验证码有效）
     * 2-已使用
     * 3-已过期
     */
    private Integer resetStatus;

    private LocalDateTime createTime;
}
