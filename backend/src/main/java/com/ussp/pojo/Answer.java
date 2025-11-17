package com.ussp.pojo;

import lombok.Data;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDateTime;

@Data
public class Answer {
    private Long id;
    private Long userId;                // 用户ID
    private Long questionId;            // 问题ID

    private String answerContent;       // 答案内容（单/多选题存JSON，文本题存字符串）
    private Integer score;              // 得分
    private LocalDateTime createTime;   // 提交时间
}
