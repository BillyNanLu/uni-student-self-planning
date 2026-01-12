package com.ussp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ussp.ai.AiModelProvider;
import com.ussp.mapper.AiChatMapper;
import com.ussp.pojo.AiChat;
import com.ussp.pojo.PageResult;
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
    public List<AiChat> getHistory(Long userId) {
        return aiChatMapper.findByUser(userId);
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



    @Override
    public PageResult<AiChat> list(Integer role, String keyword, String startTime,
                                   String endTime, Integer pageNum, Integer pageSize) {

        // 1. 确保分页参数有效
        if (pageNum == null || pageNum < 1) pageNum = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;

        // 2. 启动分页
        PageHelper.startPage(pageNum, pageSize);

        // 3. 执行查询
        List<AiChat> list = aiChatMapper.list(role, keyword, startTime, endTime);

        // 4. 获取分页信息
        PageInfo<AiChat> pageInfo = new PageInfo<>(list);

        System.out.println("原始list大小: " + list.size());          // 可能显示14
        System.out.println("分页后list大小: " + pageInfo.getList().size());  // 显示10
        System.out.println("总记录数: " + pageInfo.getTotal());

        // 5. 关键：使用pageInfo.getList()而不是原始list
        // pageInfo.getList() 才是分页后的子集数据
        return new PageResult<>(
                pageInfo.getList(),  // 这里改用pageInfo.getList()
                (int) pageInfo.getTotal()
        );
    }

    @Override
    public AiChat getById(Long id) {
        return aiChatMapper.getById(id);
    }

    @Override
    public List<AiChat> getSessionContext(String sessionId) {
        return aiChatMapper.getSessionContext(sessionId);
    }
}