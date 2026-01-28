package com.ussp.mapper;

import com.ussp.dto.ExamQueryDTO;
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





    /** 分页查询 */
    @Select("""
    SELECT 
        e.*,
        d.name AS direction_name
    FROM exam e
    LEFT JOIN direction d ON e.direction_id = d.id
    WHERE 
        (#{keyword} IS NULL OR #{keyword} = '' OR e.name LIKE CONCAT('%', #{keyword}, '%'))
        AND (#{direction} IS NULL OR e.direction_id = #{direction})
        AND (#{status} IS NULL OR e.status = #{status})
    ORDER BY e.id DESC
    LIMIT #{offset}, #{pageSize}
""")
    List<Exam> page(
            @Param("keyword") String keyword,
            @Param("direction") Integer direction,
            @Param("status") Integer status,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    /** 查询总数 */
    @Select("""
    SELECT COUNT(*)
    FROM exam e
    WHERE 
        (#{keyword} IS NULL OR #{keyword} = '' OR e.name LIKE CONCAT('%', #{keyword}, '%'))
        AND (#{direction} IS NULL OR e.direction_id = #{direction})
        AND (#{status} IS NULL OR e.status = #{status})
""")
    Integer count(
            @Param("keyword") String keyword,
            @Param("direction") Integer direction,
            @Param("status") Integer status
    );


    /** 查询详情 */
    @Select("""
        SELECT e.*, d.name AS direction_name
        FROM exam e
        LEFT JOIN direction d ON e.direction_id = d.id
        WHERE e.id = #{id}
    """)
    Exam getById(Integer id);

    /** 添加 */
    @Insert("""
        INSERT INTO exam(name, date, description, direction_id, link, status)
        VALUES(#{name}, #{date}, #{description}, #{directionId}, #{link}, #{status})
    """)
    void insert(Exam exam);

    /** 修改 */
    @Update("""
        UPDATE exam
        SET name = #{name},
            date = #{date},
            description = #{description},
            direction_id = #{directionId},
            link = #{link},
            status = #{status}
        WHERE id = #{id}
    """)
    void update(Exam exam);

    /** 删除 */
    @Delete("DELETE FROM exam WHERE id = #{id}")
    void delete(Integer id);

    /** 修改状态 */
    @Update("UPDATE exam SET status = #{status} WHERE id = #{id}")
    void updateStatus(Integer id, Integer status);

    @Select("SELECT COUNT(*) FROM exam")
    Long countExam();
}