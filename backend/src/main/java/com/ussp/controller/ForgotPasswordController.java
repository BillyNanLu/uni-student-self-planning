package com.ussp.controller;

import com.ussp.pojo.Result;
import com.ussp.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/send-captcha")
    public Result<String> sendCaptcha(@RequestBody Map<String, String> params) {
        String mail = forgotPasswordService.sendCaptcha(
                params.get("username"),
                params.get("email")
        );
        return Result.success(mail);
    }

    @PostMapping("/reset-password")
    public Result<?> resetPassword(@RequestBody Map<String, String> params) {
        forgotPasswordService.resetPassword(
                params.get("username"),
                params.get("email"),
                params.get("code"),
                params.get("password")
        );
        return Result.success("密码重置成功");
    }
}
