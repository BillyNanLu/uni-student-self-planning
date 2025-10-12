package com.ussp.controller;

import com.ussp.pojo.Result;
import com.ussp.pojo.User;
import com.ussp.service.UserService;
import com.ussp.utils.JwtUtil;
import com.ussp.utils.Md5Util;
import com.ussp.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

    // TODO: Login
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                                @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        // 判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名错误");
        }

        // 判断密码是否正确 loginUser对象中的password是密文
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            // 登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            // 把token存到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 12, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    // TODO: 获取用户信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        // 根据用户名查询用户
        /*Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    // TODO: 用户自己修改自己的个人信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        // 更新用户信息
        userService.update(user);

        // 更新后再查一次数据库，确保返回的是最新数据
        User updatedUser = userService.findByUsername(user.getUsername());

        // 返回标准响应格式
        return new Result<>(0, "信息更新成功", updatedUser);
    }

    // TODO: 用户自己修改自己的密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        // 1. 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("参数不能为空");
        }

        // 原密码是否正确
        // 调用userService根据用户名拿到原密码，再和old_pwd比对
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }

        // newPwd 和 oldPwd是否一样
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次密码不一致");
        }

        // 2. 更新密码
        userService.updatePwd(newPwd);

        return Result.success("密码更新成功，请重新登录");
    }


}
