package com.ussp.controller;

import com.ussp.dto.WorkorderQueryDTO;
import com.ussp.dto.WorkorderStatusDTO;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.Result;
import com.ussp.pojo.Workorder;
import com.ussp.service.WorkorderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/workorder")
public class AdminWorkorderController {

    @Resource
    private WorkorderService workorderService;

    /** 分页查询工单 */
    @GetMapping("/page")
    public Result<PageResult<Workorder>> page(WorkorderQueryDTO dto) {
        return Result.success(workorderService.getPage(dto));
    }

    /** 获取工单详情 */
    @GetMapping("/{id}")
    public Result<Workorder> detail(@PathVariable Long id) {
        return Result.success(workorderService.getDetail(id));
    }

    /** 更新工单状态、处理备注、时间 */
    @PutMapping("/{id}/status")
    public Result<?> updateStatus(
            @PathVariable Long id,
            @RequestBody WorkorderStatusDTO dto
    ) {
        workorderService.updateStatus(id, dto);
        return Result.success("工单处理成功");
    }
}
