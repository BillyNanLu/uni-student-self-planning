package com.ussp.controller;

import com.ussp.pojo.Career;
import com.ussp.pojo.Result;
import com.ussp.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/career")
public class CareerController {

    @Autowired
    private CareerService careerService;

    // ------------------ 按方向获取职业列表 ------------------
    @GetMapping("/list")
    public Result<List<Career>> list(@RequestParam Integer directionId) {
        List<Career> list = careerService.getByDirection(directionId);
        return Result.success(list);
    }

    // ------------------ 获取单个职业详情 ------------------
    @GetMapping("/{id}")
    public Result<Career> get(@PathVariable Integer id) {
        Career career = careerService.getById(id);
        return Result.success(career);
    }

    // ------------------ 新增职业 ------------------
    @PostMapping("/add")
    public Result<String> add(@RequestBody Career career) {
        careerService.addCareer(career);
        return Result.success("新增成功");
    }

    // ------------------ 修改职业 ------------------
    @PutMapping("/update")
    public Result<String> update(@RequestBody Career career) {
        careerService.updateCareer(career);
        return Result.success("更新成功");
    }

    // ------------------ 删除职业 ------------------
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        careerService.deleteCareer(id);
        return Result.success("删除成功");
    }
}
