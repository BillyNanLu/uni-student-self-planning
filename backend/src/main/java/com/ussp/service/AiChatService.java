package com.ussp.service;

import com.ussp.pojo.AiChat;

import java.util.List;

public interface AiChatService {

    List<AiChat> getHistory(Long userId, String sessionId);

    void saveMessage(AiChat chat);

    String getAiResponse(Long userId, String sessionId, String userMessage);

    void clearHistory(Long userId, String sessionId);
}