package com.ussp.service.impl;

import com.ussp.dto.PlanTemplateUpdateDTO;
import com.ussp.mapper.PlanTemplateMapper;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.PlanTemplate;
import com.ussp.service.PlanTemplateService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanTemplateServiceImpl implements PlanTemplateService {

    @Resource
    private PlanTemplateMapper mapper;

    @Override
    public PageResult<PlanTemplate> listTemplates(String keyword, String direction, Integer status, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<PlanTemplate> records = mapper.selectPage(keyword, direction, status, offset, pageSize);
        Long total = mapper.count(keyword, direction, status);
        return new PageResult<>(records, total.intValue());
    }

    @Override
    public PlanTemplate getTemplateDetail(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void updateTemplate(PlanTemplateUpdateDTO dto) {
        int rows = mapper.updateTemplate(dto);
        if (rows == 0) {
            throw new RuntimeException("更新失败，模板不存在");
        }
    }

    @Override
    public void updateTemplateStatus(Long id, Integer status) {
        mapper.updateStatus(id, status);
    }
}
