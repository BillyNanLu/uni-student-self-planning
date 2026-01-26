package com.ussp.controller;

import com.ussp.pojo.Result;
import com.ussp.pojo.Subject;
import com.ussp.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 获取所有学科信息
     */
    @GetMapping("/list")
    public Result<List<Subject>> getAllSubjects() {
        List<Subject> list = subjectService.getAll();
        return Result.success(list);
    }



}
