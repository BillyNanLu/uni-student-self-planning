package com.ussp.mapper;

import com.ussp.pojo.Exam;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ExamMapper {

    @Select("<script>" +
            "SELECT * FROM exam " +
            "WHERE 1=1 " +
            "<if test='status != null'> AND status = #{status} </if>" +
            "<if test='directionId != null'> AND direction_id = #{directionId} </if>" +
            "<if test='year != null'> AND date LIKE CONCAT('%', #{year}, '%') </if>" +
            "ORDER BY create_time ASC" +
            "</script>")
    List<Exam> findExams(
            @Param("directionId") Integer directionId,
            @Param("year") Integer year,
            @Param("status") Integer status
    );


    @Delete("DELETE FROM exam WHERE id = #{id}")
    int deleteExamById(Long id);

    // 按名称查重
    @Select("SELECT * FROM exam WHERE name = #{name} LIMIT 1")
    Exam findByName(@NotEmpty String name);

    // 按ID查询
    @Select("SELECT * FROM exam WHERE id = #{id}")
    Exam findById(@NotNull Integer id);

    // 插入考试信息
    @Insert("INSERT INTO exam (name, date, description, direction_id, link, status, create_time) " +
            "VALUES (#{name}, #{date}, #{description}, #{directionId}, #{link}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertExam(Exam exam);


    // 动态更新考试信息
    @Update("<script>" +
            "UPDATE exam " +
            "<set>" +
            "  <if test='name != null'> name = #{name}, </if>" +
            "  <if test='date != null'> date = #{date}, </if>" +
            "  <if test='description != null'> description = #{description}, </if>" +
            "  <if test='directionId != null'> direction_id = #{directionId}, </if>" +
            "  <if test='link != null'> link = #{link}, </if>" +
            "  <if test='status != null'> status = #{status}, </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    int updateExam(Exam exam);
}