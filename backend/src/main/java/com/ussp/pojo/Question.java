package com.ussp.pojo;

import lombok.Data;
import org.apache.ibatis.mapping.FetchType;

@Data
public class Question {

    private Long id;

    private Long questionnaireId;   // 所属问卷ID
    private String content;         // 问题内容
    private Byte type;              // 类型（1单选，2多选，3文本，4日期）

    private String options;         // JSON 格式选项

    private Integer score;          // 题目分值或权重（用于测评分析）
    private Integer orderNum;       // 排序号
}
