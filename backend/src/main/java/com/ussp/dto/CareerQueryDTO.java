package com.ussp.dto;

import lombok.Data;

@Data
public class CareerQueryDTO {

    private String keyword;
    private Integer direction;
    private Integer status;

    private Integer pageNum;
    private Integer pageSize;

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
