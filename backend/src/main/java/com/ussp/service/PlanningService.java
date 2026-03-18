package com.ussp.service;

import com.ussp.dto.AiDirectionRequestDTO;
import com.ussp.dto.AiDirectionResponseDTO;
import com.ussp.dto.AiReportRequestDTO;
import com.ussp.dto.ConfirmDirectionDTO;
import com.ussp.pojo.UserPlan;
import com.ussp.vo.DirectionMatchVO;
import com.ussp.vo.UserProfileVO;

import java.util.List;

public interface PlanningService {

    UserProfileVO getUserProfileTags(Long userId);

    DirectionMatchVO getDirectionResult(Long userId);

    void updateFinalDirection(ConfirmDirectionDTO dto);

    /**
     * 触发为用户生成AI报告并保存到 user_plan.ai_report（或 user_plan.generated_plan）
     * 返回生成后的文本内容（同步）。生产环境建议异步任务并通过 websocket/轮询通知。
     */
    String generateAiReportForUser(Long userId, AiReportRequestDTO request);

    String getLatestAiReport(Long userId);

    List<UserPlan> getUserPlanHistory(Long userId);

    AiDirectionResponseDTO generateDirection(Long userId, AiDirectionRequestDTO request);

    AiDirectionResponseDTO getLatestDirectionResult(Long userId);
}
