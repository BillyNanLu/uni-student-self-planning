package com.ussp.mapper;

import com.ussp.pojo.Exam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}