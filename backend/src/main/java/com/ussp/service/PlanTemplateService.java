package com.ussp.service;

import com.ussp.dto.PlanTemplateUpdateDTO;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.PlanTemplate;

public interface PlanTemplateService {

    PageResult<PlanTemplate> listTemplates(
            String keyword,
            String direction,
            Integer status,
            Integer pageNum,
            Integer pageSize
    );

    PlanTemplate getTemplateDetail(Long id);

    void updateTemplate(PlanTemplateUpdateDTO dto);

    void updateTemplateStatus(Long id, Integer status);
}
