package com.ussp.mapper;

import com.ussp.pojo.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlanningMapper {
    /** 查询用户所有答案（用于标签分析） */
    @Select("SELECT * FROM answer WHERE user_id = #{userId}")
    public abstract List<Answer> findAnswersByUserId(Long userId);
}
