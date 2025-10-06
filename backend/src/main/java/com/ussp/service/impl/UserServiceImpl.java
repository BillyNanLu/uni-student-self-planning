package com.ussp.service.impl;

import com.ussp.mapper.UserMapper;
import com.ussp.pojo.User;
import com.ussp.service.UserService;
import com.ussp.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password, String email, String phone, String name) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
        // 注册
        userMapper.register(username, md5String, email, phone, name);
    }
}
