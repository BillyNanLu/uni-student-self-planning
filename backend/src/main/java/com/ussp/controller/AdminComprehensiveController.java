package com.ussp.controller;

import com.ussp.pojo.Result;
import com.ussp.pojo.Subject;
import com.ussp.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/subjects")
public class AdminComprehensiveController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 修改学科信息
     */
    @PutMapping("/{id}")
    public Result<Void> updateSubject(
            @PathVariable Integer id,
            @RequestBody Subject subject
    ) {
        subject.setId(id);
        subjectService.updateSubject(subject);
        return Result.success();
    }
}
