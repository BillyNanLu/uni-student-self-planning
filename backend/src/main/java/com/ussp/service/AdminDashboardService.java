package com.ussp.service;

import com.ussp.dto.PasswordUpdateDTO;

import java.util.List;
import java.util.Map;

public interface AdminDashboardService {

    Map<String, Object> getUserCount();

    Long getQuestionnaireCount();

    Long getQuestionCount();

    Long getGeneratedCount();

    Long getAICount();

    Long getResourceCount();

    Long getWorkorderCount();

    Map<String, Object> getUserDirectionCount();

    /**
     * 获取 AI 规划生成趋势（按月份）
     */
    List<Integer> getPlanTrend(Integer year);

    boolean updateAdminPassword(PasswordUpdateDTO dto);
}
