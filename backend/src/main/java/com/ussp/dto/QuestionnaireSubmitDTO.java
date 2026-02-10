package com.ussp.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionnaireSubmitDTO {
    private Long userId;
    private String grade;
    private String major;
    private String direction;
    private List<AnswerDTO> answers;
}
