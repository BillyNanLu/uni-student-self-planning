package com.ussp.mapper;

import com.ussp.pojo.Career;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CareerMapper {

    // 查询某方向的所有职业
    @Select("SELECT * FROM career WHERE direction_id = #{directionId} AND status = 1 ORDER BY id")
    List<Career> findByDirection(Integer directionId);

    // 查询单个
    @Select("SELECT * FROM career WHERE id = #{id}")
    Career findById(Integer id);

    // 新增职业
    @Insert("INSERT INTO career(direction_id, name, detail, extra_field, extra_type, status) " +
            "VALUES(#{directionId}, #{name}, #{detail}, #{extraField}, #{extraType}, #{status})")
    void insert(Career career);

    // 更新职业
    @Update("UPDATE career SET " +
            "direction_id=#{directionId}, name=#{name}, detail=#{detail}, " +
            "extra_field=#{extraField}, extra_type=#{extraType}, status=#{status} " +
            "WHERE id=#{id}")
    void update(Career career);

    // 删除
    @Delete("DELETE FROM career WHERE id = #{id}")
    void delete(Integer id);
}
