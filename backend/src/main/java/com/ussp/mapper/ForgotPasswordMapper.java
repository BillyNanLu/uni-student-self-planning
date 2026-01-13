package com.ussp.mapper;

import com.ussp.pojo.PwdResetRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ForgotPasswordMapper {

    // 插入重置记录
    @Insert("""
        INSERT INTO pwd_reset_record(username, email, code, user_status, reset_status)
        VALUES(#{username}, #{email}, #{code}, #{userStatus}, #{resetStatus})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PwdResetRecord record);

    // 更新验证码和状态
    @Update("""
        UPDATE pwd_reset_record
        SET code = #{code}, reset_status = #{resetStatus}
        WHERE id = #{id}
    """)
    int updateCodeAndStatus(@Param("id") Long id,
                            @Param("code") String code,
                            @Param("resetStatus") Integer resetStatus);

    // 更新状态
    @Update("""
        UPDATE pwd_reset_record
        SET reset_status = #{resetStatus}
        WHERE id = #{id}
    """)
    int updateStatus(@Param("id") Long id,
                     @Param("resetStatus") Integer resetStatus);

    // 查询最近一条有效记录
    @Select("""
        SELECT * FROM pwd_reset_record
        WHERE username = #{username}
          AND email = #{email}
        ORDER BY create_time DESC
        LIMIT 1
    """)
    PwdResetRecord selectLatest(@Param("username") String username,
                                @Param("email") String email);


    /**
     * 分页查询
     */
    @Select("""
        <script>
        SELECT
            id,
            username,
            email,
            code,
            user_status,
            reset_status,
            create_time
        FROM pwd_reset_record
        <where>
            <if test="keyword != null and keyword != ''">
                AND (
                    username LIKE CONCAT('%', #{keyword}, '%')
                    OR email LIKE CONCAT('%', #{keyword}, '%')
                    OR code LIKE CONCAT('%', #{keyword}, '%')
                )
            </if>
            <if test="userStatus != null and userStatus != ''">
                AND user_status = #{userStatus}
            </if>
            <if test="resetStatus != null and resetStatus != ''">
                AND reset_status = #{resetStatus}
            </if>
        </where>
        ORDER BY create_time DESC
        LIMIT #{size} OFFSET #{offset}
        </script>
    """)
    List<PwdResetRecord> selectPage(
            @Param("offset") Integer offset,
            @Param("size") Integer size,
            @Param("keyword") String keyword,
            @Param("userStatus") Integer userStatus,
            @Param("resetStatus") Integer resetStatus
    );


    /**
     * 统计总数
     */
    @Select("""
        <script>
        SELECT COUNT(*)
        FROM pwd_reset_record
        <where>
            <if test="keyword != null and keyword != ''">
                AND (
                    username LIKE CONCAT('%', #{keyword}, '%')
                    OR email LIKE CONCAT('%', #{keyword}, '%')
                    OR code LIKE CONCAT('%', #{keyword}, '%')
                )
            </if>
            <if test="userStatus != null and userStatus != ''">
                AND user_status = #{userStatus}
            </if>
            <if test="resetStatus != null and resetStatus != ''">
                AND reset_status = #{resetStatus}
            </if>
        </where>
        </script>
    """)
    Integer count(
            @Param("keyword") String keyword,
            @Param("userStatus") Integer userStatus,
            @Param("resetStatus") Integer resetStatus
    );
}
