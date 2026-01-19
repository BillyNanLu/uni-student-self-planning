package com.ussp.mapper;

import com.ussp.pojo.Further;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FurtherMapper {

    @Select("SELECT * FROM further WHERE direction_id = #{directionId} AND status = 1 ORDER BY id DESC")
    List<Further> getByDirection(Integer directionId);

    // 分页查询
    @Select("""
            SELECT f.*,
                    d.name AS direction_name
            FROM further f
            LEFT JOIN direction d ON f.direction_id = d.id
            WHERE (#{keyword} IS NULL OR f.title LIKE CONCAT('%', #{keyword}, '%'))
                  AND (#{directionId} IS NULL OR f.direction_id = #{directionId})
                  AND (#{status} IS NULL OR f.status = #{status})
            LIMIT #{offset}, #{pageSize}
            """)
    List<Further> selectPage(String keyword, Integer directionId, Integer status, Integer offset, Integer pageSize);

    // 总数
    @Select("""
            SELECT COUNT(*)
            FROM further
            WHERE (#{keyword} IS NULL OR title LIKE CONCAT('%',#{keyword},'%'))
              AND (#{directionId} IS NULL OR direction_id = #{directionId})
              AND (#{status} IS NULL OR status = #{status})
            """)
    Integer count(String keyword, Integer directionId, Integer status);

    // 获取单条
    @Select("SELECT f.*, d.name AS direction_name FROM further f LEFT JOIN direction d ON f.direction_id = d.id WHERE f.id = #{id}")
    @Results({
            @Result(column = "direction_name", property = "directionName")
    })
    Further selectById(Integer id);

    // 插入
    @Insert("""
            INSERT INTO further(direction_id, title, link, description, type, status)
            VALUES(#{directionId}, #{title}, #{link}, #{description}, #{type}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Further further);

    // 更新
    @Update("""
            UPDATE further
            SET direction_id = #{directionId},
                title = #{title},
                link = #{link},
                description = #{description},
                type = #{type},
                status = #{status}
            WHERE id = #{id}
            """)
    void update(Further further);

    // 删除
    @Delete("DELETE FROM further WHERE id = #{id}")
    void delete(Integer id);

    // 更新状态
    @Update("UPDATE further SET status = #{status} WHERE id = #{id}")
    void updateStatus(Integer id, Integer status);

    @Select("SELECT COUNT(*) FROM further")
    Long countFurther();
}
