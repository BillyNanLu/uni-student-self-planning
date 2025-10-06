package com.ussp.mapper;

import com.ussp.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    // 注册用户
    @Insert("insert into user(username, password, email, phone, name, create_time, last_login) " +
            "values(#{username}, #{md5String}, #{email}, #{phone}, #{name}, now(), now())")
    void register(String username, String md5String, String email, String phone, String name);
}
