package com.ussp.service.impl;

import com.ussp.mapper.UserMapper;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.User;
import com.ussp.service.UserService;
import com.ussp.utils.Md5Util;
import com.ussp.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void updateLoginTime(Long id) {
        userMapper.updateLoginTime(id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

    @Override
    public String updateAvatar(MultipartFile file) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        // 存储目录（项目根目录 /uploads/）
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 文件名：时间戳 + 原始名
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败");
        }

        // 存入数据库的路径（例如：/uploads/xxx.png）
        String avatarPath = "/uploads/" + fileName;

        userMapper.updateAvatar(avatarPath, id);

        return avatarPath;
    }

    @Override
    public PageResult<User> getUserList(Integer role, String keyword, Integer pageNum, Integer pageSize) {
        if (keyword == null) keyword = "";
        keyword = "%" + keyword + "%";
        int offset = (pageNum - 1) * pageSize;

        List<User> users = userMapper.listUsers(role, keyword, offset, pageSize);
        int total = userMapper.countUsers(role, keyword);

        return new PageResult<>(users, total);
    }

    @Override
    public void addUser(User user) {
        String password = user.getUsername() + "123";
        String md5String = Md5Util.getMD5String(password);
        user.setPassword(md5String);
        user.setCreateTime(LocalDateTime.now());
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void resetPassword(Long id, String newPassword) {
        String pwd = Md5Util.getMD5String(newPassword);
        userMapper.resetPassword(id, pwd);
    }

    @Override
    public User getUserDetail(Long id) {
        return userMapper.getById(id);
    }
}
