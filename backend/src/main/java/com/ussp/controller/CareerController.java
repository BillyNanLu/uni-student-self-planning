package com.ussp.controller;

import com.ussp.dto.CareerQueryDTO;
import com.ussp.dto.StatusUpdateDTO;
import com.ussp.pojo.Career;
import com.ussp.pojo.PageResult;
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

}
