package com.ussp.service;

import com.ussp.pojo.PageResult;
import com.ussp.pojo.PwdResetRecord;

public interface ForgotPasswordService {

    String sendCaptcha(String username, String email);

    void resetPassword(String username, String email, String code, String newPassword);

    /**
     * 密码重置记录分页查询
     */
    PageResult<PwdResetRecord> pagePwdResetRecord(
            Integer page,
            Integer size,
            String keyword,
            Integer userStatus,
            Integer resetStatus
    );
}
