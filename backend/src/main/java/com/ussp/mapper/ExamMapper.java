package com.ussp.mapper;

import com.ussp.pojo.Exam;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ExamMapper {

    // 查询全部考试（未来一年）
    @Select("""
        SELECT e.*, d.name AS directionName
        FROM exam e
        LEFT JOIN direction d ON e.direction_id = d.id
        WHERE e.status = 1
        ORDER BY e.date
    """)
    List<Exam> getExamList();

    // 根据方向查询
    @Select("""
        SELECT e.*, d.name AS directionName
        FROM exam e
        LEFT JOIN direction d ON e.direction_id = d.id
        WHERE e.status = 1 AND e.direction_id = #{directionId}
        ORDER BY e.date
    """)
    List<Exam> getExamByDirection(Integer directionId);

    // 新增考试
    @Insert("INSERT INTO exam(name, date, description, direction_id, link, status) " +
            "VALUES(#{name}, #{date}, #{description}, #{directionId}, #{link}, #{status})")
    void insertExam(Exam exam);

    // 修改考试
    @Update("UPDATE exam SET name=#{name}, date=#{date}, description=#{description}, " +
            "direction_id=#{directionId}, link=#{link}, status=#{status} WHERE id=#{id}")
    void updateExam(Exam exam);

    // 删除考试
    @Delete("DELETE FROM exam WHERE id=#{id}")
    void deleteExam(@Param("id") Long id);
}