package com.ussp.dto;

import lombok.Data;

@Data
public class WorkorderSubmitDTO {
    private String type;
    private String priority;
    private String content;
    private String contact;
}
