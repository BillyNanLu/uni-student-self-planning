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

    @Override
    public Question addQuestion(Question question) {
        // 检查问卷是否存在且启用
        if (questionMapper.findQuestionnaireStatus(question.getQuestionnaireId()) != 1) {
            return null;
        }

        // 设置默认值
        if (question.getScore() == null) {
            question.setScore(0);
        }
        if (question.getOrderNum() == null) {
            question.setOrderNum(0);
        }

        // 插入题目
        int rows = questionMapper.insertQuestion(question);
        if (rows > 0) {
            return questionMapper.findById(question.getId());
        }
        return null;
    }

    @Override
    public boolean updateQuestion(Question question) {
        Question existing = questionMapper.findById(question.getId());
        if (existing == null) {
            return false;
        }

        return questionMapper.updateQuestion(question) > 0;
    }

    @Override
    public boolean deleteQuestion(Long id) {
        Question existing = questionMapper.findById(id);
        if (existing == null) {
            return false;
        }

        return questionMapper.deleteQuestion(id) > 0;
    }

    @Override
    public boolean batchDeleteQuestions(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }

        int deletedCount = questionMapper.batchDeleteQuestions(ids);
        return deletedCount > 0;
    }


    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Question> paginationQuery(Integer pageNum, Integer pageSize, Integer questionnaireId) {
        int offset = (pageNum - 1) * pageSize;
        return questionMapper.paginationQuery(offset, pageSize, questionnaireId);
    }
}
