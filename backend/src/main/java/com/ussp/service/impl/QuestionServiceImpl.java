package com.ussp.service.impl;

import com.ussp.mapper.QuestionMapper;
import com.ussp.pojo.Question;
import com.ussp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Question> getQuestionsByQuestionnaireId(Long questionnaireId) {
        return questionMapper.findByQuestionnaireId(questionnaireId);
    }
}
