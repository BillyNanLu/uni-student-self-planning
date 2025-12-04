package com.ussp.service;

import com.ussp.pojo.PageResult;
import com.ussp.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {
    // 根据用户名查询用户
    User findByUsername(String username);

    // 注册新用户
    void register(String username, String password, String email, String phone, String name);

    // 用户修改自己用户信息
    void update(User user);

    // 更新用户登录时间
    void updateLoginTime(Long id);

    // 用户修改自己密码
    void updatePwd(String newPwd);

    // 用户修改自己头像
    String updateAvatar(MultipartFile file);


    // 管理员获取用户列表（分页 + 搜索 + 角色过滤）
    PageResult<User> getUserList(Integer role, String keyword, Integer pageNum, Integer pageSize);

    // 管理员新增用户
    void addUser(User user);

    // 管理员修改用户信息
    void updateUser(User user);

    // 管理员删除用户
    void deleteUser(Long id);

    // 管理员重置用户密码
    void resetPassword(Long id, String newPassword);

    // 管理员获取用户详情
    User getUserDetail(Long id);
}
