package com.ussp.mapper;

import com.ussp.handler.JsonTypeHandler;
import com.ussp.handler.MapJsonTypeHandler;
import com.ussp.pojo.UserPlan;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserPlanMapper {

    @Select("SELECT * FROM user_plan WHERE user_id=#{userId} ORDER BY create_time DESC LIMIT 1")
    @Results({
            @Result(column = "user_score", property = "userScore", typeHandler = com.ussp.handler.MapJsonTypeHandler.class)
    })
    UserPlan findLatestByUserId(Long userId);


    @Insert("INSERT INTO user_plan(user_id, template_id, user_score, generated_plan, direction) " +
            "VALUES(#{userId}, #{templateId}, #{userScore, typeHandler=com.ussp.handler.MapJsonTypeHandler}, #{generatedPlan}, #{direction})")
    void insert(UserPlan plan);

    @Update("UPDATE user_plan SET generated_plan = #{generatedPlan}, direction = #{direction} WHERE id = #{id}")
    void updateGeneratedPlanAndDirection(UserPlan plan);

    @Select("SELECT generated_plan FROM user_plan WHERE user_id=#{userId} ORDER BY create_time DESC LIMIT 1")
    String getLatestAiReportByUserId(Long userId);

    @Select("SELECT * FROM user_plan WHERE user_id = #{userId} ORDER BY create_time DESC")
    @Results({
            @Result(column = "user_score", property = "userScore",
                    typeHandler = com.ussp.handler.MapJsonTypeHandler.class)
    })
    List<UserPlan> getUserPlansByUserId(Long userId);

    @Select("SELECT COUNT(generated_plan) FROM user_plan WHERE generated_plan IS NOT NULL")
    Long countGeneratedPlan();

    /**
     * 按月份统计 AI 规划生成数量（指定年份）
     */
    @Select("""
        SELECT 
            DATE_FORMAT(create_time, '%m') AS month,
            COUNT(*) AS count
        FROM user_plan
        WHERE YEAR(create_time) = #{year}
        GROUP BY DATE_FORMAT(create_time, '%m')
        ORDER BY month
    """)
    List<Map<String, Object>> countPlanByMonth(Integer year);
}
