package com.ussp.controller;

import com.ussp.pojo.PageResult;
import com.ussp.pojo.Result;
import com.ussp.service.AiReportService;
import com.ussp.vo.UserPlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ai-report")
public class AdminAiReportController {

    @Autowired
    private AiReportService aiReportService;

    /**
     * 后台分页查询 AI 规划报告列表
     */
    @GetMapping
    public Result<PageResult<UserPlanVO>> list(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false, defaultValue = "") String direction,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {

        PageResult<UserPlanVO> page = aiReportService.list(keyword, startTime, endTime, direction, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询一条规划详情
     */
    @GetMapping("/{id}")
    public Result<UserPlanVO> detail(@PathVariable Long id) {
        UserPlanVO detail = aiReportService.detail(id);
        return Result.success(detail);
    }
}