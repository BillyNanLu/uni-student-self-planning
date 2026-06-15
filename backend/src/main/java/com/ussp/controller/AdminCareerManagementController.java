package com.ussp.controller;

import com.ussp.dto.CareerQueryDTO;
import com.ussp.dto.StatusUpdateDTO;
import com.ussp.pojo.Career;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.Result;
import com.ussp.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCareerManagementController {

    private final CareerService careerService;

    /** 获取方向列表 */
    @GetMapping("/careersdirections")
    public Result<?> getDirections() {
        return Result.success(careerService.getDirectionList());
    }

    /** 获取职业列表（分页 + 筛选） */
    @GetMapping("/careers")
    public Result<?> getCareerList(CareerQueryDTO query) {
        PageResult<?> page = careerService.getCareerList(query);
        return Result.success(page);
    }

    /** 获取职业详情 */
    @GetMapping("/careers/{id}")
    public Result<?> getCareerDetail(@PathVariable Integer id) {
        return Result.success(careerService.getCareerDetail(id));
    }

    /** 新增职业 */
    @PostMapping("/careers")
    public Result<?> addCareer(@RequestBody Career career) {
        careerService.addCareer(career);
        return Result.success();
    }

    /** 更新职业 */
    @PutMapping("/careers")
    public Result<?> updateCareer(@RequestBody Career career) {
        careerService.updateCareer(career);
        return Result.success();
    }

    /** 删除职业 */
    @DeleteMapping("/careers/{id}")
    public Result<?> deleteCareer(@PathVariable Integer id) {
        careerService.deleteCareer(id);
        return Result.success();
    }

    /** 修改状态 */
    @PutMapping("/careers/{id}/status")
    public Result<?> updateCareerStatus(@PathVariable Integer id, @RequestBody StatusUpdateDTO req) {
        careerService.updateCareerStatus(id, req.getStatus());
        return Result.success();
    }
}
