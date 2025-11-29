package com.ussp.ai;

public interface AiModelProvider {
    /**
     * 给定 prompt 返回模型生成的文本（同步）
     */
    String generateText(String prompt);
}