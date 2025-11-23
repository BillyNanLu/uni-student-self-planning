package com.ussp.controller;

import com.ussp.pojo.Questionnaire;
import com.ussp.pojo.Result;
import com.ussp.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    // TODO: 获取所有问卷信息
    @GetMapping("/all")
    public Result<List<Questionnaire>> all() {
        List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaires(1);
        if (questionnaires == null || questionnaires.isEmpty()) {
            return new Result<>(4001, "暂无问卷信息", Collections.emptyList());
        }
        return new Result<>(0, "获取问卷信息成功", questionnaires);
    }

    // TODO: 获取问卷数量
    @GetMapping("/count")
    public int getQuestionnaireCount() {
        return questionnaireService.getQuestionnaireCount();
    }
}
