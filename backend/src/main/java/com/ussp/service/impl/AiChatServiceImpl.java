package com.ussp.service.impl;

import com.ussp.ai.AiModelProvider;
import com.ussp.mapper.AiChatMapper;
import com.ussp.pojo.AiChat;
import com.ussp.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiChatServiceImpl implements AiChatService {

    @Autowired
    private AiChatMapper aiChatMapper;

    @Autowired
    private AiModelProvider aiModelProvider;

    @Override
    public List<AiChat> getHistory(Long userId, String sessionId) {
        return aiChatMapper.findByUserAndSession(userId, sessionId);
    }

    @Override
    public void saveMessage(AiChat chat) {
        aiChatMapper.insert(chat);
    }

    @Override
    public String getAiResponse(Long userId, String sessionId, String userMessage) {
        // 1. 调用 AI
        String aiReply = aiModelProvider.generateText(userMessage);

        // 2. 保存 AI 消息
        AiChat aiMsg = new AiChat();
        aiMsg.setUserId(userId);
        aiMsg.setSessionId(sessionId);
        aiMsg.setRole(1); // AI
        aiMsg.setContent(aiReply);
        aiChatMapper.insert(aiMsg);

        return aiReply;
    }

    @Override
    public void clearHistory(Long userId, String sessionId) {
        aiChatMapper.deleteByUserAndSession(userId, sessionId);
    }
}