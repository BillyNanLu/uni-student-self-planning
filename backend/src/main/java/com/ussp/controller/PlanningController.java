package com.ussp.controller;

import com.ussp.pojo.Result;
import com.ussp.service.PlanningService;
import com.ussp.vo.UserProfileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planning")
public class PlanningController {

    @Autowired
    private PlanningService planningService;

    // TODO: 获取用户画像标签（兴趣/能力/自评）
    @GetMapping("/user-profile-tags/{userId}")
    public Result<UserProfileVO> getUserProfileTags(@PathVariable Long userId) {
        UserProfileVO vo = planningService.getUserProfileTags(userId);
        return Result.success(vo);
    }
}
