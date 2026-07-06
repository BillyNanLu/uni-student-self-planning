package com.ussp.mapper;

import com.ussp.dto.PlanTemplateUpdateDTO;
import com.ussp.pojo.PlanTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PlanTemplateMapper {

    @Select("SELECT id, direction, template_name, evaluation_rules, version, status, create_time, update_time " +
            "FROM plan_template WHERE direction = #{direction} AND status = 1 ORDER BY id ASC")
    List<PlanTemplate> findByDirection(String direction);

    @Select("SELECT id, direction, template_name, evaluation_rules, version, status, create_time, update_time " +
            "FROM plan_template WHERE id = #{id}")
    PlanTemplate findById(Long id);


    // 分页&搜索
    @Select("""
        SELECT * FROM plan_template
        WHERE (#{keyword} IS NULL OR template_name LIKE CONCAT('%', #{keyword}, '%'))
        AND (#{direction} IS NULL OR direction = #{direction})
        AND (#{status} IS NULL OR status = #{status})
        ORDER BY update_time DESC
        LIMIT #{offset}, #{pageSize}
    """)
    List<PlanTemplate> selectPage(
            @Param("keyword") String keyword,
            @Param("direction") String direction,
            @Param("status") Integer status,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    // 总数查询
    @Select("""
        SELECT COUNT(*) FROM plan_template
        WHERE (#{keyword} IS NULL OR template_name LIKE CONCAT('%', #{keyword}, '%'))
        AND (#{direction} IS NULL OR direction = #{direction})
        AND (#{status} IS NULL OR status = #{status})
    """)
    Long count(
            @Param("keyword") String keyword,
            @Param("direction") String direction,
            @Param("status") Integer status
    );

    // 详情
    @Select("SELECT * FROM plan_template WHERE id = #{id}")
    PlanTemplate selectById(Long id);

    // 更新模板
    @Update("""
        UPDATE plan_template
        SET template_name = #{templateName},
            version = #{version},
            status = #{status},
            evaluation_rules = #{evaluationRules},
            update_time = NOW()
        WHERE id = #{id}
    """)
    int updateTemplate(PlanTemplateUpdateDTO dto);

    // 更新状态
    @Update("""
        UPDATE plan_template
        SET status = #{status},
            update_time = NOW()
        WHERE id = #{id}
    """)
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
