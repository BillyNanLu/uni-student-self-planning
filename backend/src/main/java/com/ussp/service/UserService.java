package com.ussp.service;

import com.ussp.pojo.User;

public interface UserService {
    // 根据用户名查询用户
    User findByUsername(String username);

    // 注册新用户
    void register(String username, String password, String email, String phone, String name);
}
