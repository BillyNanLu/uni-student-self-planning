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
}
