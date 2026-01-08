package com.ussp.service;

import com.ussp.pojo.AiChat;
import com.ussp.pojo.PageResult;

import java.util.List;

public interface AiChatService {

    List<AiChat> getHistory(Long userId);

    void saveMessage(AiChat chat);

    String getAiResponse(Long userId, String sessionId, String userMessage);

    void clearHistory(Long userId, String sessionId);

    PageResult<AiChat> list(Integer role, String keyword, String startTime,
                            String endTime, Integer pageNum, Integer pageSize);

    AiChat getById(Long id);

    List<AiChat> getSessionContext(String sessionId);
}