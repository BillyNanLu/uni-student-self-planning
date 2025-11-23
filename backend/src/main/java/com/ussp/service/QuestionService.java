package com.ussp.service;

import com.ussp.pojo.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionsByQuestionnaireId(Long questionnaireId);
}
