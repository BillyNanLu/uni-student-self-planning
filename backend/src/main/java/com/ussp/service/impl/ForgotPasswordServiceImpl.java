package com.ussp.service.impl;

import com.ussp.mapper.ForgotPasswordMapper;
import com.ussp.mapper.UserMapper;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.PwdResetRecord;
import com.ussp.pojo.User;
import com.ussp.service.ForgotPasswordService;
import com.ussp.service.MailTemplateService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final ForgotPasswordMapper forgotPasswordMapper;
    private final UserMapper userMapper;
    private final StringRedisTemplate redisTemplate;
    private final MailTemplateService mailTemplateService;
    private final JavaMailSender mailSender;

    private static final String REDIS_PREFIX = "pwd:reset:";
    private static final long EXPIRE_MINUTES = 5;

    @Override
    @Transactional
    public String sendCaptcha(String username, String email) {

        // 1. 查询用户是否存在
        User user = userMapper.findByUsernameAndEmail(username, email);
        System.out.println(user);

        PwdResetRecord record = new PwdResetRecord();
        record.setUsername(username);
        record.setEmail(email);

        if (user == null) {
            // 非平台注册用户
            record.setUserStatus(0);
            record.setResetStatus(0);
            forgotPasswordMapper.insert(record);
            return "非平台注册用户";
        }

        // 2. 已注册用户
        record.setUserStatus(1);
        record.setResetStatus(1);

        // 生成验证码
        String captcha = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        System.out.println("验证码：" + captcha);
        record.setCode(captcha);

        forgotPasswordMapper.insert(record);

        // 3. Redis 存储
        String redisKey = REDIS_PREFIX + username + ":" + email;
        redisTemplate.opsForValue()
                .set(redisKey, captcha, EXPIRE_MINUTES, TimeUnit.MINUTES);

        // 4. 发送邮箱
        try {
            String html = mailTemplateService.buildResetPasswordCaptchaMail(captcha);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("biggerk@foxmail.com");
            helper.setTo(email);
            helper.setSubject("【大学生自我规划平台】密码重置验证码");
            helper.setText(html, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("邮件发送失败，请稍后重试");
        }

        String mail = captcha;
        return mail;
    }

    @Override
    @Transactional
    public void resetPassword(String username, String email, String code, String newPassword) {

        String redisKey = REDIS_PREFIX + username + ":" + email;
        String redisCode = redisTemplate.opsForValue().get(redisKey);

        PwdResetRecord record = forgotPasswordMapper.selectLatest(username, email);
        if (record == null || record.getResetStatus() != 1) {
            throw new RuntimeException("验证码无效或已使用");
        }

        // 超时校验（兜底）
        if (record.getCreateTime().plusMinutes(EXPIRE_MINUTES).isBefore(LocalDateTime.now())) {
            forgotPasswordMapper.updateStatus(record.getId(), 3);
            throw new RuntimeException("验证码已过期");
        }

        if (!code.equals(redisCode)) {
            throw new RuntimeException("验证码错误");
        }

        // 更新密码
        String md5Pwd = DigestUtils.md5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8));
        userMapper.updatePasswordByUsername(username, md5Pwd);

        // 更新状态
        forgotPasswordMapper.updateStatus(record.getId(), 2);

        // 删除 Redis
        redisTemplate.delete(redisKey);
    }


    @Override
    public PageResult<PwdResetRecord> pagePwdResetRecord(
            Integer page,
            Integer size,
            String keyword,
            Integer userStatus,
            Integer resetStatus
    ) {

        int offset = (page - 1) * size;

        List<PwdResetRecord> records =
                forgotPasswordMapper.selectPage(
                        offset, size, keyword, userStatus, resetStatus
                );

        Integer total =
                forgotPasswordMapper.count(
                        keyword, userStatus, resetStatus
                );

        return new PageResult<>(records, total);
    }
}
