package com.ussp.mapper;

import com.ussp.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("SELECT id, questionnaire_id, content, type, options, score, order_num " +
            "FROM question WHERE questionnaire_id = #{qid} ORDER BY order_num ASC")
    @Results({
            @Result(column = "options", property = "options",
                    typeHandler = com.ussp.handler.JsonTypeHandler.class)
    })
    List<Question> findByQuestionnaireId(@Param("qid") Long questionnaireId);


    @Select("SELECT * FROM question WHERE id = #{id}")
    Question findById(Long id);
}