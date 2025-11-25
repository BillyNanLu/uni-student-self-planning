package com.ussp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionnaireResultVO {
    private ScoreVO score;
    private DirectionResult result;
}