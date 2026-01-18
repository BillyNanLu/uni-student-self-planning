package com.ussp.service.impl;

import com.ussp.dto.WorkorderQueryDTO;
import com.ussp.dto.WorkorderStatusDTO;
import com.ussp.dto.WorkorderSubmitDTO;
import com.ussp.mapper.WorkorderMapper;
import com.ussp.pojo.PageResult;
import com.ussp.pojo.Workorder;
import com.ussp.service.WorkorderService;
import com.ussp.utils.WorkorderNoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkorderServiceImpl implements WorkorderService {

    private final WorkorderMapper workorderMapper;

    @Override
    public void submit(WorkorderSubmitDTO dto) {
        Workorder workorder = new Workorder();
        workorder.setWorkorderNo(WorkorderNoUtil.generate());
        workorder.setType(dto.getType());
        workorder.setPriority(dto.getPriority() == null ? "low" : dto.getPriority());
        workorder.setContent(dto.getContent());
        workorder.setContact(dto.getContact());
        workorder.setStatus("pending");

        workorderMapper.insert(workorder);
    }


    @Override
    public PageResult<Workorder> getPage(WorkorderQueryDTO dto) {

        Integer count = workorderMapper.countPage(dto);
        if (count == 0) {
            return new PageResult<>(Collections.emptyList(), 0);
        }

        int offset = (dto.getPage() - 1) * dto.getSize();
        List<Workorder> list = workorderMapper.page(dto, offset, dto.getSize());

        return new PageResult<>(list, count);
    }

    @Override
    public Workorder getDetail(Long id) {
        return workorderMapper.getById(id);
    }

    @Override
    public void updateStatus(Long id, WorkorderStatusDTO dto) {
        workorderMapper.updateStatus(id, dto);
    }

}
