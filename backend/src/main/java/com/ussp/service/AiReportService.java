package com.ussp.service;

import com.ussp.pojo.PageResult;
import com.ussp.vo.UserPlanVO;

public interface AiReportService {
    /**
     * 直接传已经构建好的 prompt，返回生成文本
     */
     String generateReport(String prompt);

    PageResult<UserPlanVO> list(String keyword,
                                String startTime,
                                String endTime,
                                String direction,
                                Integer pageNum,
                                Integer pageSize);

    UserPlanVO detail(Long id);
}
