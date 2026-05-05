package com.ussp.dto;

import lombok.Data;

@Data
public class ExamQueryDTO {
    private String keyword;
    private Integer direction;
    private Integer status;

    private Integer pageNum;
    private Integer pageSize;

    // 自动计算 offset，不用你手算
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}