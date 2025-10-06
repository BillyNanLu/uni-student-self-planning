package com.ussp.controller;

import com.ussp.pojo.Result;
import com.ussp.pojo.User;
import com.ussp.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // TODO: Register
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password,
                           String email,
                           String phone,
                           String name) {
        // 查询用户是否存在
        User u = userService.findByUsername(username);
        if (u == null) {
            // 创建用户
            userService.register(username, password, email, phone, name);

            // 注册后再查一次用户（含 id、create_time、role 等）用于postman测试
            User newUser = userService.findByUsername(username);

            return new Result<>(0, "注册成功", newUser);
        } else {
            // 用户已存在
            return Result.error("用户已被占用");
        }
    }


}
