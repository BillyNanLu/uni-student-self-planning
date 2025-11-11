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

    // TODO: 获取考试信息
    @GetMapping("/all")
    public Result<List<Exam>> all(
            @RequestParam(required = false) Integer direction_id,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false, defaultValue = "1") Integer status
    ) {
        List<Exam> exams = examService.getAllExams(direction_id, year, status);

        if (exams == null || exams.isEmpty()) {
            return new Result<>(4001, "暂无考试信息", Collections.emptyList());
        }

        return new Result<>(0, "获取考试信息成功", exams);
    }
}
