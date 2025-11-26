package com.ussp.mapper;

import com.ussp.pojo.Question;
import com.ussp.pojo.Questionnaire;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionnaireMapper {

    // 查询问卷（支持按状态筛选）
    @Select("<script>" +
            "SELECT * FROM questionnaire " +
            "WHERE 1=1 " +
            "<if test='status != null'> AND status = #{status} </if>" +
            "ORDER BY create_time DESC" +
            "</script>")
    List<Questionnaire> findQuestionnaires(@Param("status") Integer status);

    // 删除
    @Delete("DELETE FROM questionnaire WHERE id = #{id}")
    int deleteQuestionnaireById(Long id);

    // 查重：按标题
    @Select("SELECT * FROM questionnaire WHERE title = #{title} LIMIT 1")
    Questionnaire findByTitle(String title);

    // 按 ID 查询
    @Select("SELECT * FROM questionnaire WHERE id = #{id}")
    Questionnaire findById(Long id);

    // 插入问卷
    @Insert("INSERT INTO questionnaire(title, description, status, create_time) " +
            "VALUES(#{title}, #{description}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertQuestionnaire(Questionnaire questionnaire);

    // 动态更新
    @Update("<script>" +
            "UPDATE questionnaire " +
            "<set>" +
            "  <if test='title != null'> title = #{title}, </if>" +
            "  <if test='description != null'> description = #{description}, </if>" +
            "  <if test='status != null'> status = #{status}, </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    int updateQuestionnaire(Questionnaire questionnaire);

    // 获取问卷数量
    @Select("SELECT COUNT(*) FROM questionnaire WHERE status = 1")
    int countQuestionnaires();

    // 查询问卷标题
    @Select("SELECT title FROM questionnaire WHERE id = #{id}")
    String findTitleById(Long id);
}