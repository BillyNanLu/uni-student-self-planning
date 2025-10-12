package com.ussp.service;

import com.ussp.pojo.User;

public interface UserService {
    // 根据用户名查询用户
    User findByUsername(String username);

    // 注册新用户
    void register(String username, String password, String email, String phone, String name);

    // 用户修改自己用户信息
    void update(User user);

    // 用户修改自己密码
    void updatePwd(String newPwd);
}
