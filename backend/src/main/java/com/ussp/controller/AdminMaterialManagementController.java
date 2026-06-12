package com.ussp.controller;

import com.ussp.dto.MaterialQueryDTO;
import com.ussp.dto.MaterialUpdateDTO;
import com.ussp.mapper.DirectionMapper;
import com.ussp.pojo.Direction;
import com.ussp.pojo.Further;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.Result;
import com.ussp.service.FurtherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMaterialManagementController {

    private final FurtherService furtherService;
    private final DirectionMapper directionMapper;

    // 获取资源列表
    @GetMapping("/materials")
    public Result<PageResult<Further>> list(MaterialQueryDTO dto) {
        return Result.success(furtherService.page(dto));
    }

    // 获取资源详情
    @GetMapping("/materials/{id}")
    public Result<Further> detail(@PathVariable Integer id) {
        return Result.success(furtherService.detail(id));
    }

    // 添加资源
    @PostMapping("/materials")
    public Result<?> add(@RequestBody MaterialUpdateDTO dto) {
        furtherService.add(dto);
        return Result.success();
    }

    // 更新资源
    @PutMapping("/materials")
    public Result<?> update(@RequestBody MaterialUpdateDTO dto) {
        furtherService.update(dto);
        return Result.success();
    }

    // 删除资源
    @DeleteMapping("/materials/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        furtherService.delete(id);
        return Result.success();
    }

    // 更新状态
    @PutMapping("/materials/{id}/status")
    public Result<?> updateStatus(@PathVariable Integer id, @RequestBody MaterialUpdateDTO dto) {
        furtherService.updateStatus(id, dto.getStatus());
        return Result.success();
    }

    // 获取方向列表
    @GetMapping("/directions")
    public Result<List<Direction>> directions() {
        return Result.success(directionMapper.selectAll());
    }
}
