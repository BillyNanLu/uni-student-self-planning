package com.ussp.dto;

import lombok.Data;

@Data
public class WorkorderQueryDTO {
    private Integer page = 1;
    private Integer size = 10;
    private String keyword;
    private String status; // pending / processing / resolved
    private String type;
}
