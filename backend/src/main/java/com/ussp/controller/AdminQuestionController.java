package com.ussp.controller;

import com.ussp.pojo.Question;
import com.ussp.pojo.Result;
import com.ussp.service.QuestionService;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin/questions")
public class AdminQuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<List<Question>> paginationQuery(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "questionnaireId", required = false) Integer questionnaireId) {
        List<Question> list = questionService.paginationQuery(pageNum, pageSize, questionnaireId);
        return new Result<>(0, "查询成功", list);
    }

    // 按问卷类型展示问卷题目
    @GetMapping("/list/{questionnaireId}")
    public Result<List<Question>> getQuestions(@PathVariable Long questionnaireId) {
        List<Question> questions = questionService.getQuestionsByQuestionnaireId(questionnaireId);
        if (questions == null || questions.isEmpty()) {
            return new Result<>(2001, "问卷不存在或已停用", Collections.emptyList());
        }
        return new Result<>(0, "查询成功", questions);
    }

    // 按问卷类型展示问卷题目（兼容旧接口）
    @GetMapping("/byQuestionnaire/{id}")
    public List<Question> getQuestionsLegacy(@PathVariable Long id) {
        return questionService.getQuestionsByQuestionnaireId(id);
    }

    // 管理员新增题目
    @PostMapping
    public Result<Void> addQuestion(@RequestBody @Validated Question question) {
        Question newQuestion = questionService.addQuestion(question);
        if (newQuestion == null) {
            return Result.error("问卷不存在或已停用");
        }
        return new Result<>(0, "问题创建成功", null);
    }

    // 管理员修改题目
    @PutMapping
    public Result<Void> updateQuestion(@RequestBody @Validated Question question) {
        boolean updated = questionService.updateQuestion(question);
        if (!updated) {
            return new Result<>(2003, "问题不存在", null);
        }
        return new Result<>(0, "问题更新成功", null);
    }

    // 管理员删除单个问题
    @DeleteMapping("/{id}")
    public Result<Void> deleteQuestion(@PathVariable Long id) {
        boolean deleted = questionService.deleteQuestion(id);
        if (!deleted) {
            return new Result<>(2004, "问题不存在", null);
        }
        return new Result<>(0, "问题删除成功", null);
    }

    // 管理员批量删除问题
    @DeleteMapping("/deleteBatch")
    public Result<Void> batchDeleteQuestions(@RequestBody List<Long> ids) {
        boolean deleted = questionService.batchDeleteQuestions(ids);
        if (!deleted) {
            return new Result<>(2005, "部分问题不存在或已被删除", null);
        }
        return new Result<>(0, "批量删除成功", null);
    }
}
