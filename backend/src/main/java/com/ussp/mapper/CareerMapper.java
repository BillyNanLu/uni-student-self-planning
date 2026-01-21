package com.ussp.mapper;

import com.ussp.dto.CareerQueryDTO;
import com.ussp.pojo.Career;
import com.ussp.pojo.Direction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CareerMapper {

    // 查询某方向的所有职业
    @Select("SELECT * FROM career WHERE direction_id = #{directionId} AND status = 1 ORDER BY id")
    List<Career> findByDirection(Integer directionId);

    /** 方向列表 */
    @Select("SELECT id, name FROM direction")
    List<Direction> getDirectionList();

    /** 统计总数（分页） */
    @Select("""
            SELECT COUNT(*) FROM career 
            WHERE (#{keyword} IS NULL OR name LIKE CONCAT('%', #{keyword}, '%'))
              AND (#{direction} IS NULL OR direction_id = #{direction})
              AND (#{status} IS NULL OR status = #{status})
            """)
    Integer countCareers(CareerQueryDTO query);

    /** 列表数据（分页） */
    @Select("""
    SELECT career.*,
           direction.name as direction_name
    FROM career
    LEFT JOIN direction ON career.direction_id = direction.id
    WHERE (#{query.keyword} IS NULL OR career.name LIKE CONCAT('%', #{query.keyword}, '%'))
      AND (#{query.direction} IS NULL OR direction_id = #{query.direction})
      AND (#{query.status} IS NULL OR status = #{query.status})
    ORDER BY id DESC
    LIMIT ${(query.pageNum - 1) * query.pageSize}, #{query.pageSize}
    """)
    List<Career> getCareerList(@Param("query") CareerQueryDTO query);

    /** 职业详情 */
    @Select("SELECT career.*, " +
            "direction.name AS direction_name " +
            "FROM career " +
            "LEFT JOIN direction ON career.direction_id = direction.id " +
            "WHERE career.id = #{id}")
    @Results({
            @Result(column = "direction_name", property = "directionName")
    })
    Career getCareerDetail(Integer id);

    /** 新增 */
    @Insert("""
            INSERT INTO career(direction_id, name, detail, extra_field, extra_type, status)
            VALUES(#{directionId}, #{name}, #{detail}, #{extraField}, #{extraType}, #{status})
            """)
    void insertCareer(Career career);

    /** 更新 */
    @Update("""
            UPDATE career
            SET direction_id = #{directionId},
                name = #{name},
                detail = #{detail},
                extra_field = #{extraField},
                extra_type = #{extraType},
                status = #{status}
            WHERE id = #{id}
            """)
    void updateCareer(Career career);

    /** 更新状态 */
    @Update("UPDATE career SET status = #{status} WHERE id = #{id}")
    void updateCareerStatus(@Param("id") Integer id, @Param("status") Integer status);

    /** 删除 */
    @Delete("DELETE FROM career WHERE id = #{id}")
    void deleteCareer(Integer id);

    @Select("SELECT COUNT(*) FROM career")
    Long countCareer();
}
