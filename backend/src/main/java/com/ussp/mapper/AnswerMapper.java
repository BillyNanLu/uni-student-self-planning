package com.ussp.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AnswerMapper {

    @Insert("INSERT INTO answer(user_id,question_id,answer_content) VALUES(#{userId},#{qid},#{content})")
    void insertAnswer(@Param("userId") Long userId,
                      @Param("qid") Long questionId,
                      @Param("content") String content);
}
