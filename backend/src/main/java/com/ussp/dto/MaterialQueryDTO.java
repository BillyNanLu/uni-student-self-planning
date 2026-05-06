package com.ussp.dto;

import lombok.Data;

@Data
public class MaterialQueryDTO {
    private String keyword;
    private String direction;
    private String status;
    private Integer pageNum;
    private Integer pageSize;
}
