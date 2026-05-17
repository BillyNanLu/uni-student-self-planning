package com.ussp.service;

import com.ussp.dto.QuestionnaireSubmitDTO;
import com.ussp.pojo.Exam;
import com.ussp.pojo.Questionnaire;
import com.ussp.vo.QuestionnaireResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuestionnaireService {
    // 获取所有问卷（可根据状态筛选）
    List<Questionnaire> getAllQuestionnaires(Integer status);

    // 删除问卷
    boolean deleteQuestionnaireById(Long id);

    // 添加问卷
    Questionnaire addQuestionnaire(Questionnaire questionnaire);

    // 更新问卷
    Questionnaire updateQuestionnaire(Long id, Questionnaire questionnaire);

    // 获取问卷数量
    int getQuestionnaireCount();

    QuestionnaireResultVO processSubmit(QuestionnaireSubmitDTO dto);

    List<Questionnaire> paginationQuery(Integer pageNum, Integer pageSize);

    Questionnaire findById(Long id);
}
