package com.ussp.service;

import com.ussp.pojo.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionsByQuestionnaireId(Long questionnaireId);

    // 管理员新增题目
    Question addQuestion(Question question);

    // 管理员修改题目
    boolean updateQuestion(Question question);

    // 管理员删除单个问题
    boolean deleteQuestion(Long id);

    // 管理员批量删除问题
    boolean batchDeleteQuestions(List<Long> ids);

    List<Question> paginationQuery(Integer pageNum, Integer pageSize, Integer questionnaireId);
}
