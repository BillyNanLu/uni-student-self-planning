package com.ussp.controller;

import com.ussp.pojo.Questionnaire;
import com.ussp.pojo.Result;
import com.ussp.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/questionnaire")
public class AdminQuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    //分页
    @GetMapping("/page")
    public Result<List<Questionnaire>> paginationQuery(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<Questionnaire> list = questionnaireService.paginationQuery(pageNum, pageSize);
        return new Result<>(0, "查询成功", list);
    }

    // 管理员通过问卷id获取问卷所有信息
    @GetMapping("/{id}")
    public Result<Questionnaire> getQuestionnaireById(@PathVariable Long id) {
        Questionnaire questionnaire = questionnaireService.findById(id);
        return new Result<>(0, "查询成功", questionnaire);
    }

    // 管理员新增问卷
    @PostMapping
    public Result<Questionnaire> addQuestionnaire(@RequestBody @Validated Questionnaire questionnaire) {
        Questionnaire newQuestionnaire = questionnaireService.addQuestionnaire(questionnaire);
        if (newQuestionnaire == null) {
            return Result.error("问卷标题不能为空");
        }
        return new Result<>(0, "创建成功", newQuestionnaire);
    }

    // 管理员修改问卷
    @PutMapping("/{id}")
    public Result<Questionnaire> updateQuestionnaire(@PathVariable Long id, @RequestBody @Validated Questionnaire questionnaire) {
        Questionnaire updated = questionnaireService.updateQuestionnaire(id, questionnaire);
        if (updated == null) {
            return new Result<>(404, "未找到该问卷", null);
        }
        return new Result<>(0, "修改成功", null);
    }

    // 管理员删除问卷
    @DeleteMapping("/{id}")
    public Result<Void> deleteQuestionnaire(@PathVariable Long id) {
        boolean deleted = questionnaireService.deleteQuestionnaireById(id);
        if (!deleted) {
            return new Result<>(404, "未找到该问卷", null);
        }
        return new Result<>(0, "删除成功", null);
    }
}
