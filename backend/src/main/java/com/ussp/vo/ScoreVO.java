package com.ussp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreVO {
    private int kaoyan;
    private int kaogong;
    private int jiuye;

    public String maxDirection() {
        if (kaoyan >= kaogong && kaoyan >= jiuye) return "考研";
        if (kaogong >= kaoyan && kaogong >= jiuye) return "考公";
        return "就业";
    }
}
