package com.ussp.controller;

import com.ussp.pojo.AiChat;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.Result;
import com.ussp.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/ai-chats")
public class AdminAiChatController {

    @Autowired
    private AiChatService aiChatService;

    // TODO: 管理员获取聊天记录
    @GetMapping
    public Result<PageResult<AiChat>> list(
            @RequestParam(required = false, defaultValue = "-1") Integer role,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        PageResult<AiChat> pageResult =
                aiChatService.list(role, keyword, startTime, endTime, pageNum, pageSize);
        System.out.println("进入管理员聊天记录接口");
        return Result.success(pageResult);
    }

    // TODO: 管理员获取聊天记录
    @GetMapping("/{id}")
    public Result<AiChat> getOne(@PathVariable Long id) {
        AiChat data = aiChatService.getById(id);
        return Result.success(data);
    }

    // TODO: 管理员获取同一 Session 的上下文
    @GetMapping("/session/{sessionId}")
    public Result<List<AiChat>> getSessionContext(@PathVariable String sessionId) {
        List<AiChat> list = aiChatService.getSessionContext(sessionId);
        return Result.success(list);
    }
}
