package com.ussp.controller;

import com.ussp.pojo.Exam;
import com.ussp.pojo.Result;
import com.ussp.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    // ------------------ 获取未来一年考试列表 ------------------
    @GetMapping("/list")
    public Result<List<Exam>> list() {
        List<Exam> list = examService.getExamList();
        return Result.success(list);
    }

    // ------------------ 根据方向获取考试 ------------------
    @GetMapping("/listByDirection")
    public Result<List<Exam>> listByDirection(@RequestParam Integer directionId) {
        List<Exam> list = examService.getExamByDirection(directionId);
        return Result.success(list);
    }
}
