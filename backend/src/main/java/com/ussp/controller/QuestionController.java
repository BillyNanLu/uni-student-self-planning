package com.ussp.controller;


import com.ussp.pojo.Question;
import com.ussp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    // TODO: 按问卷类型展示问卷题目
    @GetMapping("/byQuestionnaire/{id}")
    public List<Question> getQuestions(@PathVariable Long id) {
        return questionService.getQuestionsByQuestionnaireId(id);
    }

}
