package com.ussp.mapper;

import com.ussp.pojo.Further;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FurtherMapper {

    @Select("SELECT * FROM further WHERE direction_id = #{directionId} AND status = 1 ORDER BY id DESC")
    List<Further> getByDirection(Integer directionId);

    // 管理员：按方向查询资源列表（directionId 可为空）
    @Select("<script>" +
            "SELECT * FROM further " +
            "<where>" +
            "  <if test='directionId != null'> direction_id = #{directionId} </if>" +
            "</where>" +
            "ORDER BY id DESC" +
            "</script>")
    List<Further> adminList(@Param("directionId") Integer directionId);

    // 管理员：根据ID查询
    @Select("SELECT * FROM further WHERE id = #{id}")
    Further getById(@Param("id") Integer id);

    // 管理员：新增资源
    @Insert("INSERT INTO further(direction_id, title, content, url, create_time, update_time) " +
            "VALUES(#{directionId}, #{title}, #{content}, #{url}, NOW(), NOW())")
    int insert(Further further);

    // 管理员：修改资源
    @Update("<script>" +
            "UPDATE further " +
            "<set>" +
            " <if test='directionId != null'> direction_id = #{directionId}, </if>" +
            " <if test='title != null'> title = #{title}, </if>" +
            " <if test='content != null'> content = #{content}, </if>" +
            " <if test='url != null'> url = #{url}, </if>" +
            " update_time = NOW() " +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    int update(Further further);

    // 管理员：删除
    @Delete("DELETE FROM further WHERE id = #{id}")
    int delete(@Param("id") Integer id);
}
