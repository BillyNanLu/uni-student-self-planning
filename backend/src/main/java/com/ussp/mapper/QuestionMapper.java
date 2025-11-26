package com.ussp.mapper;

import com.ussp.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper {

    @Select("SELECT id, questionnaire_id, content, type, options, score, order_num " +
            "FROM question WHERE questionnaire_id = #{qid} ORDER BY order_num ASC")
    @Results({
            @Result(column = "options", property = "options",
                    typeHandler = com.ussp.handler.JsonTypeHandler.class)
    })
    List<Question> findByQuestionnaireId(@Param("qid") Long questionnaireId);


    // 查询问题
    @Select("SELECT id, questionnaire_id, type, options FROM question WHERE id = #{id}")
    @Results({
            @Result(column = "options", property = "options",
                    typeHandler = com.ussp.handler.JsonTypeHandler.class)
    })
    Question findById(Long id);

    // 查询问卷标题
    @Select("SELECT title FROM questionnaire WHERE id = #{id}")
    String findTitleById(Long id);
}