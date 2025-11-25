package com.ussp.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long questionId;
    private Object value; // 单选 String，多选 List<String>，文本 String
}
