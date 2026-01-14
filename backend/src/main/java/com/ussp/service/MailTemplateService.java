package com.ussp.service;

public interface MailTemplateService {

    /**
     * 构建密码重置验证码 HTML 邮件
     *
     * @param captcha 验证码
     * @return HTML 字符串
     */
    String buildResetPasswordCaptchaMail(String captcha);
}
