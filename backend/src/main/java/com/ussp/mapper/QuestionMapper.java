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



    // 查询问卷状态
    @Select("SELECT status FROM questionnaire WHERE id = #{id}")
    Integer findQuestionnaireStatus(Long id);

    // 插入题目
    @Insert("INSERT INTO question (questionnaire_id, content, type, options, score, order_num) " +
            "VALUES (#{questionnaireId}, #{content}, #{type}, #{options, typeHandler=com.ussp.handler.JsonTypeHandler}, #{score}, #{orderNum})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertQuestion(Question question);

    // 更新题目
    @Update("<script>" +
            "UPDATE question " +
            "<set>" +
            "  <if test='content != null'> content = #{content}, </if>" +
            "  <if test='type != null'> type = #{type}, </if>" +
            "  <if test='options != null'> options = #{options, typeHandler=com.ussp.handler.JsonTypeHandler}, </if>" +
            "  <if test='score != null'> score = #{score}, </if>" +
            "  <if test='orderNum != null'> order_num = #{orderNum}, </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    int updateQuestion(Question question);

    // 删除题目
    @Delete("DELETE FROM question WHERE id = #{id}")
    int deleteQuestion(Long id);

    // 批量删除题目
    @Delete("<script>" +
            "DELETE FROM question WHERE id IN " +
            "<foreach collection='list' item='id' open='(' separator=',' close=')'>" +
            "  #{id}" +
            "</foreach>" +
            "</script>")
    int batchDeleteQuestions(List<Long> ids);


    @Select("<script>" +
            "SELECT id, questionnaire_id, content, type, options, score, order_num " +
            "FROM question WHERE 1=1 " +
            "<if test='questionnaireId != null'> AND questionnaire_id = #{questionnaireId} </if> " +
            "LIMIT #{offset}, #{pageSize}" +
            "</script>")
    @Results({
            @Result(column = "options", property = "options",
                    typeHandler = com.ussp.handler.JsonTypeHandler.class)
    })
    List<Question> paginationQuery(
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize,
            @Param("questionnaireId") Integer questionnaireId
    );

    @Select("SELECT COUNT(*) FROM question")
    Long countQuestion();
}