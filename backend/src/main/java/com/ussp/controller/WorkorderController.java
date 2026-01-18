package com.ussp.controller;

import com.ussp.dto.WorkorderSubmitDTO;
import com.ussp.pojo.Result;
import com.ussp.service.WorkorderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workorder")
@RequiredArgsConstructor
public class WorkorderController {

    private final WorkorderService workorderService;

    /** 用户提交工单 */
    @PostMapping
    public Result submit(@RequestBody WorkorderSubmitDTO dto) {
        workorderService.submit(dto);
        return Result.success("提交成功，感谢您的反馈！");
    }

}
