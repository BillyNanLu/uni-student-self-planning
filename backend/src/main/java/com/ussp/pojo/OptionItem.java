package com.ussp.pojo;

import lombok.Data;

import java.util.List;

@Data
public class OptionItem {
    private String key;        // 前端传的值，比如 "A"
    private String label;      // 显示内容
    private Weights weights;   // 分数对象
    private List<String> tags;       // 标签

    @Data
    public static class Weights {
        private Integer kaoyan;   // 考研
        private Integer kaogong;  // 考公
        private Integer jiuye;    // 就业
    }
}