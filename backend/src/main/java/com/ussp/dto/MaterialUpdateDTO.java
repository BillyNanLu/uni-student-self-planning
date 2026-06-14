package com.ussp.dto;

import lombok.Data;

@Data
public class MaterialUpdateDTO {
    private Integer id;
    private Integer directionId;
    private String title;
    private String link;
    private String type;
    private String description;
    private Integer status;
}