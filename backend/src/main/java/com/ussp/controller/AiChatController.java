package com.ussp.controller;

import com.ussp.pojo.AiChat;
import com.ussp.pojo.Result;
import com.ussp.service.AdminSysConfigService;
import com.ussp.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai-chat")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;
    @Autowired
    private AdminSysConfigService sysConfigService;


    // ------------------ 获取聊天历史 ------------------
    @GetMapping("/history")
    public Result<List<AiChat>> history(@RequestParam Long userId) {
        List<AiChat> list = aiChatService.getHistory(userId);
        return Result.success(list);
    }

    // ------------------ 保存用户消息 ------------------
    @PostMapping("/save")
    public Result<String> save(@RequestParam Long userId,
                               @RequestParam String sessionId,
                               @RequestParam Integer role,
                               @RequestParam String content) {

        AiChat chat = new AiChat();
        chat.setUserId(userId);
        chat.setSessionId(sessionId);
        chat.setRole(role);
        chat.setContent(content);

        aiChatService.saveMessage(chat);
        return Result.success("ok");
    }

    // ------------------ 获取 AI 回复 ------------------
    @PostMapping("/response")
    public Result<String> response(@RequestParam Long userId,
                                   @RequestParam String sessionId,
                                   @RequestParam String content) {

        // 检查 AI 开关状态
        String aiEnable = sysConfigService.getConfigValue("ai_service_enable");
        if (!"true".equalsIgnoreCase(aiEnable)) {
            return Result.error("AI 聊天服务已关闭，请稍后再试");
        }

        // ---------------- 正常逻辑 ----------------

        // 保存用户消息
        AiChat userMsg = new AiChat();
        userMsg.setUserId(userId);
        userMsg.setSessionId(sessionId);
        userMsg.setRole(0); // 用户
        userMsg.setContent(content);
        aiChatService.saveMessage(userMsg);

        // 获取 AI 回复
        String aiText = aiChatService.getAiResponse(userId, sessionId, content);
        return Result.success(aiText);
    }

    // ------------------ 清空聊天记录 ------------------
    @DeleteMapping("/clear")
    public Result<String> clear(@RequestParam Long userId,
                                @RequestParam String sessionId) {
        aiChatService.clearHistory(userId, sessionId);
        return Result.success("ok");
    }
}