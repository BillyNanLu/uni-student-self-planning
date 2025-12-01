package com.ussp.controller;

import com.ussp.pojo.Further;
import com.ussp.pojo.Result;
import com.ussp.service.FurtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/further")
public class FurtherController {

    @Autowired
    private FurtherService furtherService;

    // ------------------ 根据方向获取资源 ------------------
    @GetMapping("/listByDirection")
    public Result<List<Further>> listByDirection(@RequestParam Integer directionId) {
        List<Further> list = furtherService.getListByDirection(directionId);
        return Result.success(list);
    }

    // ------------------ 管理员：获取资源列表 ------------------
    @GetMapping("/list")
    public Result<List<Further>> list(
            @RequestParam(required = false) Integer directionId) {

        List<Further> list = furtherService.adminList(directionId);
        return Result.success(list);
    }

    // ------------------ 管理员：获取单个资源详情 ------------------
    @GetMapping("/{id}")
    public Result<Further> detail(@PathVariable Integer id) {
        Further f = furtherService.getById(id);
        return Result.success(f);
    }

    // ------------------ 管理员：新增资源 ------------------
    @PostMapping("/add")
    public Result<String> add(@RequestBody Further further) {
        furtherService.add(further);
        return Result.success("新增成功");
    }

    // ------------------ 管理员：修改资源 ------------------
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable Integer id,
                                 @RequestBody Further further) {
        further.setId(id);
        furtherService.update(further);
        return Result.success("修改成功");
    }

    // ------------------ 管理员：删除资源 ------------------
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        furtherService.delete(id);
        return Result.success("删除成功");
    }
}
