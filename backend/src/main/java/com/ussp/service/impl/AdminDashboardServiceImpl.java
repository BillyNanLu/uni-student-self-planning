package com.ussp.service.impl;

import com.ussp.dto.PasswordUpdateDTO;
import com.ussp.mapper.*;
import com.ussp.pojo.User;
import com.ussp.service.AdminDashboardService;
import com.ussp.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserPlanMapper userPlanMapper;
    @Autowired
    private AiChatMapper aiChatMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private FurtherMapper furtherMapper;
    @Autowired
    private CareerMapper careerMapper;
    @Autowired
    private WorkorderMapper workorderMapper;
    @Autowired
    private UserDirectionMapper userDirectionMapper;

    @Override
    public Map<String, Object> getUserCount() {
        Long studentCount = userMapper.countUserByRole(0);
        Long adminCount = userMapper.countUserByRole(1);

        Map<String, Object> result = new HashMap<>();
        result.put("studentCount", studentCount);
        result.put("adminCount", adminCount);

        return result;
    }

    @Override
    public Long getQuestionnaireCount() {
        return questionnaireMapper.countQuestionnaire();
    }

    @Override
    public Long getQuestionCount() {
        return questionMapper.countQuestion();
    }

    @Override
    public Long getGeneratedCount() {
        return userPlanMapper.countGeneratedPlan();
    }

    @Override
    public Long getAICount() {
        return aiChatMapper.countAIChat();
    }

    @Override
    public Long getResourceCount() {
        return examMapper.countExam() + furtherMapper.countFurther() + careerMapper.countCareer();
    }

    @Override
    public Long getWorkorderCount() {
        return workorderMapper.countWorkorder();
    }


    @Override
    public Map<String, Object> getUserDirectionCount() {
        Long kaoyanCount = userDirectionMapper.countUserByFinalDirection("考研");
        Long kaogongCount = userDirectionMapper.countUserByFinalDirection("考公");
        Long jiuyeCount = userDirectionMapper.countUserByFinalDirection("就业");

        Map<String, Object> result = new HashMap<>();
        result.put("kaoyanCount", kaoyanCount);
        result.put("kaogongCount", kaogongCount);
        result.put("jiuyeCount", jiuyeCount);

        return result;
    }


    @Override
    public List<Integer> getPlanTrend(Integer year) {

        if (year == null) {
            year = LocalDate.now().getYear(); // 默认今年
        }

        // 查询数据库原始结果
        List<Map<String, Object>> dbResult = userPlanMapper.countPlanByMonth(year);

        // 构造 12 个月的趋势图数据（默认全 0）
        List<Integer> monthlyCounts = new ArrayList<>(Collections.nCopies(12, 0));

        // 把数据库数据填入对应月份
        for (Map<String, Object> row : dbResult) {
            int month = Integer.parseInt(row.get("month").toString()); // "01" => 1
            int count = Integer.parseInt(row.get("count").toString());
            monthlyCounts.set(month - 1, count);
        }

        return monthlyCounts;
    }

    @Override
    public boolean updateAdminPassword(PasswordUpdateDTO dto) {
        // 1. 查找用户
        User user = userMapper.getByUsername(dto.getUsername());
        if (user == null) {
            return false;
        }

        // 2. 校验旧密码（MD5）
        String oldPwdMd5 = Md5Util.getMD5String(dto.getOldPassword());
        if (!oldPwdMd5.equals(user.getPassword())) {
            return false; // 旧密码不一致
        }

        // 3. 加密新密码
        String newPwdMd5 = Md5Util.getMD5String(dto.getNewPassword());

        // 4. 更新密码
        userMapper.updatePassword(user.getId(), newPwdMd5);

        return true;
    }
}
