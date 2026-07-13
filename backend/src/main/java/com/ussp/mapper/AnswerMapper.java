package com.ussp.mapper;

import com.ussp.dto.AnswerDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnswerMapper {

    @Select("SELECT COUNT(1) FROM answer WHERE user_id=#{userId}")
    int exitsAnswerById(@Param("userId") Long userId);

    @Delete("DELETE FROM answer WHERE user_id=#{userId}")
    void deleteAnswerByUserId(@Param("userId") Long userId);

    @Insert("INSERT INTO answer(user_id,question_id,answer_content) VALUES(#{userId},#{qid},#{content})")
    void insertAnswer(@Param("userId") Long userId,
                      @Param("qid") Long questionId,
                      @Param("content") String content);

    @Select("""
                SELECT
                    IFNULL(GROUP_CONCAT(t.answer_content SEPARATOR '\\\\n'), '') AS other_self
                FROM answer t
                JOIN question q ON t.question_id = q.id AND q.type = 3
                WHERE t.user_id = #{userId};  -- 直接查用户的所有文本题答案（只有最新一批）
            """)
    // 指定返回类型为 String，MyBatis 自动映射
    @ResultType(String.class)
    String findTextAnswerByUserId(@Param("userId") Long userId);

    @Select("SELECT question_id AS questionId, answer_content AS value FROM answer WHERE user_id=#{userId}")
    List<AnswerDTO> findAllByUserId(Long userId);
}
