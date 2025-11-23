package com.ussp.service.impl;

import com.ussp.mapper.QuestionnaireMapper;
import com.ussp.pojo.Questionnaire;
import com.ussp.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Override
    public List<Questionnaire> getAllQuestionnaires(Integer status) {
        return questionnaireMapper.findQuestionnaires(status);
    }

    @Override
    public boolean deleteQuestionnaireById(Long id) {
        return questionnaireMapper.deleteQuestionnaireById(id) > 0;
    }

    @Override
    public Questionnaire addQuestionnaire(Questionnaire questionnaire) {
        // 1. 校验标题是否重复
        Questionnaire existing = questionnaireMapper.findByTitle(questionnaire.getTitle());
        if (existing != null) {
            return null; // 已存在同名问卷
        }

        // 2. 默认状态
        if (questionnaire.getStatus() == null) {
            questionnaire.setStatus((byte) 1);
        }
        questionnaire.setCreateTime(LocalDateTime.now());

        // 3. 插入
        int rows = questionnaireMapper.insertQuestionnaire(questionnaire);
        if (rows > 0) {
            return questionnaireMapper.findById(questionnaire.getId());
        }

        return null;
    }

    @Override
    public Questionnaire updateQuestionnaire(Long id, Questionnaire questionnaire) {
        // 1. 检查是否存在
        Questionnaire existing = questionnaireMapper.findById(id);
        if (existing == null) return null;

        // 2. 更新非 null 字段
        questionnaire.setId(id);
        int updated = questionnaireMapper.updateQuestionnaire(questionnaire);

        // 3. 返回最新数据
        return updated > 0 ? questionnaireMapper.findById(id) : null;
    }

    @Override
    public int getQuestionnaireCount() {
        return questionnaireMapper.countQuestionnaires();
    }
}