package com.ussp.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OptionItem {
    private String key;        // 前端传的值，比如 "A"
    private String label;      // 显示内容
    private Weights weights;   // 分数对象
    private List<String> tags;       // 标签

    // 兴趣测评（RIASEC 六维度）
    private Map<String, Integer> interest;

    // 能力测评（五维度）
    private Map<String, Integer> ability;

    // 自我评价（）
    private Map<String, Integer> selfEvaluation;

    @Data
    public static class Weights {
        private Integer kaoyan;   // 考研
        private Integer kaogong;  // 考公
        private Integer jiuye;    // 就业
    }
}