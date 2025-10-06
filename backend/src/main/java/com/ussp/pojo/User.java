package com.ussp.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库表：user
 */
// lombok 在编译阶段，为实体类自动生成setter getter toString
// @Data 是 @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode 的组合注解
@Data
public class User {
    private Long id;                 // 主键ID
    private String username;         // 登录名
    private String password;         // 密码（加密存储，MD5）
    private String avatar;           // 头像URL
    private String name;             // 姓名
    private String email;            // 邮箱
    private String phone;            // 电话
    private Integer role;            // 角色（0=学生, 1=管理员）
    private LocalDateTime createTime; // 注册时间
    private LocalDateTime lastLogin;  // 最近登录时间
}
