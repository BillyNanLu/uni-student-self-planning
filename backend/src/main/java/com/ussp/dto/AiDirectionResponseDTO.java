package com.ussp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiDirectionResponseDTO {
    private String content;      // AI 推荐理由文本
    private String direction;    // AI 提取出的方向（考研/考公/就业）
}