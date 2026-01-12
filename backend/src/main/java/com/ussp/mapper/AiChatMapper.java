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

    @Select("SELECT * FROM ai_chat WHERE user_id = #{userId} ORDER BY id ASC")
    List<AiChat> findByUser(Long userId);

    @Insert("INSERT INTO ai_chat(user_id, session_id, role, content, create_time) " +
            "VALUES(#{userId}, #{sessionId}, #{role}, #{content}, NOW())")
    void insert(AiChat chat);

    @Delete("DELETE FROM ai_chat WHERE user_id = #{userId} AND session_id = #{sessionId}")
    void deleteByUserAndSession(Long userId, String sessionId);

    // 分页条件查询
    List<AiChat> list(@Param("role") Integer role,
                      @Param("keyword") String keyword,
                      @Param("startTime") String startTime,
                      @Param("endTime") String endTime);

    // 查询单条聊天记录
    @Select("SELECT ac.*, u.username " +
            "FROM ai_chat ac LEFT JOIN user u ON ac.user_id = u.id " +
            "WHERE ac.id = #{id}")
    AiChat getById(Long id);

    // 查询同一个 Session 的历史消息（按时间升序）
    @Select("SELECT ac.*, u.username " +
            "FROM ai_chat ac LEFT JOIN user u ON ac.user_id = u.id " +
            "WHERE ac.session_id = #{sessionId} " +
            "ORDER BY ac.create_time ASC")
    List<AiChat> getSessionContext(String sessionId);

    @Select("SELECT COUNT(*) FROM ai_chat WHERE role = 1")
    Long countAIChat();
}
