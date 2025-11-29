package com.ussp.mapper;

import com.ussp.pojo.AiChat;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AiChatMapper {

    @Select("SELECT * FROM ai_chat WHERE user_id = #{userId} AND session_id = #{sessionId} ORDER BY id ASC")
    List<AiChat> findByUserAndSession(Long userId, String sessionId);

    @Insert("INSERT INTO ai_chat(user_id, session_id, role, content, create_time) " +
            "VALUES(#{userId}, #{sessionId}, #{role}, #{content}, NOW())")
    void insert(AiChat chat);

    @Delete("DELETE FROM ai_chat WHERE user_id = #{userId} AND session_id = #{sessionId}")
    void deleteByUserAndSession(Long userId, String sessionId);
}
