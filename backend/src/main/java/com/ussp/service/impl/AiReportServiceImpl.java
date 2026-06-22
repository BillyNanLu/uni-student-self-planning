package com.ussp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ussp.ai.AiModelProvider;
import com.ussp.mapper.AiReportMapper;
import com.ussp.pojo.PageResult;
import com.ussp.service.AiReportService;
import com.ussp.vo.UserPlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiReportServiceImpl implements AiReportService {

    @Autowired
    private AiModelProvider provider;

    @Autowired
    private AiReportMapper aiReportMapper;

    @Override
    public String generateReport(String prompt) {
        // 直接调用 provider，生产环境可做限流、重试、缓存
        return provider.generateText(prompt);
    }

    @Override
    public PageResult<UserPlanVO> list(String keyword, String startTime, String endTime, String direction, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;

        PageHelper.startPage(pageNum, pageSize);

        List<UserPlanVO> list = aiReportMapper.list(keyword, startTime, endTime, direction);
        PageInfo<UserPlanVO> pageInfo = new PageInfo<>(list);

        return new PageResult<>(
                pageInfo.getList(),
                (int) pageInfo.getTotal()
        );
    }

    @Override
    public UserPlanVO detail(Long id) {
        return aiReportMapper.detail(id);
    }
}