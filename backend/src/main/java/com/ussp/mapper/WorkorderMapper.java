package com.ussp.mapper;

import com.ussp.dto.WorkorderQueryDTO;
import com.ussp.dto.WorkorderStatusDTO;
import com.ussp.pojo.Workorder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WorkorderMapper {

    @Insert("""
        INSERT INTO workorder(
            workorder_no, type, priority, content, contact, status
        )
        VALUES(
            #{workorderNo}, #{type}, #{priority}, #{content}, #{contact}, #{status}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Workorder workorder);


    /** 统计总数 */
    @Select("""
        SELECT COUNT(*)
        FROM workorder
        WHERE (#{dto.keyword} IS NULL OR #{dto.keyword} = '' 
               OR content LIKE CONCAT('%', #{dto.keyword}, '%'))
          AND (#{dto.status} IS NULL OR #{dto.status} = '' 
               OR status = #{dto.status})
    """)
    Integer countPage(@Param("dto") WorkorderQueryDTO dto);

    /** 分页列表 */
    @Select("""
        SELECT *
        FROM workorder
        WHERE (#{dto.keyword} IS NULL OR #{dto.keyword} = '' 
               OR content LIKE CONCAT('%', #{dto.keyword}, '%'))
          AND (#{dto.status} IS NULL OR #{dto.status} = '' 
               OR status = #{dto.status})
          AND (#{dto.type} IS NULL OR #{dto.type} = '' 
               OR type = #{dto.type})
        ORDER BY created_at DESC
        LIMIT #{offset}, #{size}
    """)
    List<Workorder> page(
            @Param("dto") WorkorderQueryDTO dto,
            @Param("offset") Integer offset,
            @Param("size") Integer size
    );

    /** 详情 */
    @Select("SELECT * FROM workorder WHERE id = #{id}")
    Workorder getById(Long id);


    /** 更新工单状态、处理备注、处理时间 */
    @Update("""
        UPDATE workorder
        SET status = #{dto.status},
            handled_at = #{dto.handledAt},
            remark = #{dto.remark}
        WHERE id = #{id}
    """)
    void updateStatus(@Param("id") Long id,
                      @Param("dto") WorkorderStatusDTO dto);

    @Select("SELECT COUNT(*) FROM workorder")
    Long countWorkorder();
}
