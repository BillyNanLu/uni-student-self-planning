package com.ussp.controller;

import com.ussp.dto.PasswordUpdateDTO;
import com.ussp.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ussp.pojo.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    /**
     * 获取用户数量统计（普通用户数 + 管理员数）
     */
    @GetMapping("/userCount")
    public Result<Map<String, Object>> getUserCount() {

        Map<String, Object> data = adminDashboardService.getUserCount();

        return Result.success(data);
    }

    /**
     * 获取问卷数量
     */
    @GetMapping("/questionnaireCount")
    public Result<Long> getQuestionnaireCount() {
        Long count = adminDashboardService.getQuestionnaireCount();
        return Result.success(count);
    }

    /**
     * 获取问题数量
     */
    @GetMapping("/questionCount")
    public Result<Long> getQuestionCount() {
        Long count = adminDashboardService.getQuestionCount();
        return Result.success(count);
    }

    /**
     * 获取规划生成数
     */
    @GetMapping("/generatedCount")
    public Result<Long> getGeneratedCount() {
        Long count = adminDashboardService.getGeneratedCount();
        return Result.success(count);
    }

    /**
     * 获取AI总回复数
     */
    @GetMapping("/aiCount")
    public Result<Long> getAICount() {
        Long count = adminDashboardService.getAICount();
        return Result.success(count);
    }

    /**
     * 获取总资源数
     */
    @GetMapping("/resourceCount")
    public Result<Long> getResourceCount() {
        Long count = adminDashboardService.getResourceCount();
        return Result.success(count);
    }

    /**
     * 获取总工单条数
     */
    @GetMapping("/workorderCount")
    public Result<Long> getWorkorderCount() {
        Long count = adminDashboardService.getWorkorderCount();
        return Result.success(count);
    }

    /**
     * 获取三个方向人数
     */
    @GetMapping("/userDirectionCount")
    public Result<Map<String, Object>> getUserDirectionCount() {
        Map<String, Object> data = adminDashboardService.getUserDirectionCount();

        return Result.success(data);
    }

    /**
     * 获取 AI 规划生成趋势（按月统计）
     */
    @GetMapping("/plan-trend")
    public Result<List<Integer>> getPlanTrend(@RequestParam(required = false) Integer year) {

        List<Integer> trend = adminDashboardService.getPlanTrend(year);

        return Result.success(trend);
    }

    /**
     * 管理员修改自己密码
     */
    @PutMapping("/updatePassword")
    public Result<?> updatePassword(@RequestBody PasswordUpdateDTO dto) {
        boolean success = adminDashboardService.updateAdminPassword(dto);

        if (success) {
            return Result.success("密码修改成功");
        } else {
            return Result.error("旧密码错误，修改失败");
        }
    }
}
