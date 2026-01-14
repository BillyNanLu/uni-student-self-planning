package com.ussp.service.impl;

import com.ussp.service.MailTemplateService;
import org.springframework.stereotype.Service;

@Service
public class MailTemplateServiceImpl implements MailTemplateService {

    @Override
    public String buildResetPasswordCaptchaMail(String captcha) {

        return (
"""
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>密码重置验证码</title>
</head>
<body style="margin:0;padding:0;background-color:#f5f7fa;font-family:Arial,Helvetica,sans-serif;">
  <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f5f7fa;padding:30px 0;">
    <tr>
      <td align="center">
        <table width="600" cellpadding="0" cellspacing="0"
               style="background:#ffffff;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,0.05);padding:30px;">
          <tr>
            <td style="font-size:20px;color:#333333;font-weight:bold;padding-bottom:20px;">
              密码重置验证码
            </td>
          </tr>

          <tr>
            <td style="font-size:14px;color:#555555;line-height:1.8;">
              您好：
              <br/><br/>
              我们收到了您的<strong>密码重置</strong>请求，请使用以下验证码完成身份验证：
            </td>
          </tr>

          <tr>
            <td align="center" style="padding:25px 0;">
              <div style="
                  display:inline-block;
                  background:#409EFF;
                  color:#ffffff;
                  font-size:26px;
                  font-weight:bold;
                  letter-spacing:6px;
                  padding:12px 30px;
                  border-radius:6px;">
                %s
              </div>
            </td>
          </tr>

          <tr>
            <td style="font-size:14px;color:#555555;line-height:1.8;">
              验证码 <strong>5 分钟内有效</strong>，请尽快完成操作。
              <br/>
              若非本人操作，请忽略此邮件，您的账号不会受到影响。
            </td>
          </tr>

          <tr>
            <td style="border-top:1px solid #eeeeee;margin-top:30px;padding-top:20px;
                       font-size:12px;color:#999999;line-height:1.6;">
              本邮件由 <strong>大学生自我规划平台</strong> 系统自动发送，请勿回复。
              <br/>
              为保障您的账号安全，请勿将验证码泄露给他人。
            </td>
          </tr>

        </table>
      </td>
    </tr>
  </table>
</body>
</html>
""").formatted(captcha);
    }
}
