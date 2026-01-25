package com.ussp.pojo;

import lombok.Data;

@Data
public class Subject {
    private Integer id;
    private String name;
    private String representativeMajors;
    private String postgraduate; // JSON 字符串
    private String civilService; // JSON 字符串
    private String marketEmployment; // JSON 字符串
}
