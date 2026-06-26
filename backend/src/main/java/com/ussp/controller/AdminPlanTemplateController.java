package com.ussp.controller;

import com.ussp.dto.PlanTemplateUpdateDTO;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.PlanTemplate;
import com.ussp.pojo.Result;
import com.ussp.service.PlanTemplateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/ai-templates")
public class AdminPlanTemplateController {

    @Resource
    private PlanTemplateService service;

    // 列表查询
    @GetMapping
    public Result<PageResult<PlanTemplate>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return Result.success(service.listTemplates(keyword, direction, status, pageNum, pageSize));
    }

    // 模板详情
    @GetMapping("/{id}")
    public Result<PlanTemplate> detail(@PathVariable Long id) {
        return Result.success(service.getTemplateDetail(id));
    }

    // 更新模板
    @PutMapping
    public Result<Void> update(@RequestBody PlanTemplateUpdateDTO dto) {
        service.updateTemplate(dto);
        return Result.success(null);
    }

    // 更新状态
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> payload
    ) {
        Integer status = payload.get("status");
        service.updateTemplateStatus(id, status);
        return Result.success(null);
    }
}