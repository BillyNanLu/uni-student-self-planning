package com.ussp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ussp.dto.AiDirectionRequestDTO;
import com.ussp.dto.AiDirectionResponseDTO;
import com.ussp.dto.AiReportRequestDTO;
import com.ussp.dto.ConfirmDirectionDTO;
import com.ussp.pojo.Result;
import com.ussp.pojo.UserPlan;
import com.ussp.service.PlanningService;
import com.ussp.service.QuestionnaireService;
import com.ussp.vo.DirectionMatchVO;
import com.ussp.vo.UserProfileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/planning")
public class PlanningController {

    @Autowired
    private PlanningService planningService;

    // TODO: 获取用户画像标签（兴趣/能力/自评）
    @GetMapping("/user-profile-tags/{userId}")
    public Result<UserProfileVO> getUserProfileTags(@PathVariable Long userId) {
        UserProfileVO vo = planningService.getUserProfileTags(userId);
        return Result.success(vo);
    }

    // TODO: 获取测评结果展示
    @GetMapping("/result/{userId}")
    public Result<DirectionMatchVO> getDirectionResult(@PathVariable Long userId) {
        return Result.success(planningService.getDirectionResult(userId));
    }

    // TODO: 用户确认方向
    @PostMapping("/confirmDirection")
    public Result confirmDirection(@RequestBody ConfirmDirectionDTO dto) {
        planningService.updateFinalDirection(dto);
        return Result.success();
    }

    // TODO：触发 AI 发展方向生成
    @PostMapping("/generate-direction/{userId}")
    public Result<AiDirectionResponseDTO> generateDirection(
            @PathVariable Long userId,
            @RequestBody AiDirectionRequestDTO request) {

        AiDirectionResponseDTO dto = planningService.generateDirection(userId, request);
        return Result.success(dto);
    }

    // TODO：获取用户最新的 AI 发展方向
    @GetMapping("/direction/latest/{userId}")
    public Result<AiDirectionResponseDTO> getLatestDirection(@PathVariable Long userId) {
        return Result.success(planningService.getLatestDirectionResult(userId));
    }

    // TODO：触发 AI 报告生成
    @PostMapping("/generate-report/{userId}")
    public Result<String> generateAiReport(
            @PathVariable Long userId,
            @RequestBody AiReportRequestDTO request) {

        String report = planningService.generateAiReportForUser(userId, request);
        return Result.success(report);
    }

    // TODO: 获取用户最新的 AI 报告
    @GetMapping("/report/latest/{userId}")
    public Result<String> getLatestAiReport(@PathVariable Long userId) {
        return Result.success(planningService.getLatestAiReport(userId));
    }

    // TODO: 获取用户所有 AI 报告
    @GetMapping("report/history/{userId}")
    public Result getUserPlanHistory(@PathVariable Long userId) {
        List<UserPlan> list = planningService.getUserPlanHistory(userId);
        return Result.success(list);
    }

}
