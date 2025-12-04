package com.ussp.mapper;

import com.ussp.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    // 注册用户
    @Insert("insert into user(username, password, email, phone, name, create_time, last_login) " +
            "values(#{username}, #{md5String}, #{email}, #{phone}, #{name}, now(), now())")
    void register(String username, String md5String, String email, String phone, String name);

    // 用户修改自己的用户信息
    @Update("update user set email=#{email}, phone=#{phone}, name=#{name}, grade=#{grade}, major=#{major} where id =#{id}")
    void update(User user);

    // 用户自己修改密码
    @Update("update user set password=#{md5String} where id =#{id}")
    void updatePwd(String md5String, Integer id);

    @Update("UPDATE user SET major=#{major}, grade=#{grade} WHERE id=#{userId}")
    void updateMajorAndGrade(@Param("userId") Long userId,
                             @Param("major") String major,
                             @Param("grade") String grade);

    // Map<String, Object> findBasicInfoById(Long userId);

    @Update("update user set avatar = #{avatar} where id = #{id}")
    void updateAvatar(String avatar, Integer id);


    /** 查询列表（分页 + 角色过滤 + 搜索） */
    @Select("""
        SELECT * FROM user
        WHERE (#{role} IS NULL OR role = #{role})
          AND (username LIKE #{keyword}
               OR name LIKE #{keyword}
               OR email LIKE #{keyword})
        ORDER BY create_time ASC
        LIMIT #{offset}, #{pageSize}
    """)
    List<User> listUsers(@Param("role") Integer role,
                         @Param("keyword") String keyword,
                         @Param("offset") Integer offset,
                         @Param("pageSize") Integer pageSize);

    /** 统计总数 */
    @Select("""
        SELECT COUNT(*) FROM user
        WHERE (#{role} IS NULL OR role = #{role})
          AND (username LIKE #{keyword}
               OR name LIKE #{keyword}
               OR email LIKE #{keyword})
    """)
    int countUsers(@Param("role") Integer role,
                   @Param("keyword") String keyword);

    /** 根据 ID 查询 */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(Long id);

    /** 插入用户 */
    @Insert("""
        INSERT INTO user(username, password, avatar, name, email, phone, grade, major, role, create_time)
        VALUES(#{username}, #{password}, #{avatar}, #{name}, #{email}, #{phone}, #{grade}, #{major}, #{role}, now())
    """)
    void insertUser(User user);

    /** 更新用户 */
    @Update("""
        UPDATE user SET
            username = #{username},
            avatar = #{avatar},
            name = #{name},
            email = #{email},
            phone = #{phone},
            grade = #{grade},
            major = #{major},
            role = #{role}
        WHERE id = #{id}
    """)
    void updateUser(User user);

    /** 删除用户 */
    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(Long id);

    /** 重置密码 */
    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    void resetPassword(@Param("id") Long id, @Param("password") String password);

    @Select("SELECT grade, major FROM user WHERE id = #{id}")
    Map<String, Object> findBasicInfoById(Long userId);

    @Update("UPDATE user SET last_login = now() WHERE id = #{id}")
    void updateLoginTime(Long id);
}
