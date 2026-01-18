package com.ussp.service;

import com.ussp.dto.WorkorderQueryDTO;
import com.ussp.dto.WorkorderStatusDTO;
import com.ussp.dto.WorkorderSubmitDTO;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.Workorder;

public interface WorkorderService {

    void submit(WorkorderSubmitDTO dto);

    PageResult<Workorder> getPage(WorkorderQueryDTO dto);

    Workorder getDetail(Long id);

    void updateStatus(Long id, WorkorderStatusDTO dto);
}
