package com.ussp.service.impl;

import com.ussp.dto.MaterialQueryDTO;
import com.ussp.dto.MaterialUpdateDTO;
import com.ussp.mapper.FurtherMapper;
import com.ussp.pojo.Further;
import com.ussp.pojo.PageResult;
import com.ussp.service.FurtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FurtherServiceImpl implements FurtherService {

    @Autowired
    private FurtherMapper furtherMapper;

    @Override
    public List<Further> getListByDirection(Integer directionId) {
        return furtherMapper.getByDirection(directionId);
    }

    @Override
    public PageResult<Further> page(MaterialQueryDTO dto) {

        Integer directionId = null;
        if (dto.getDirection() != null && !dto.getDirection().isEmpty()) {
            directionId = Integer.valueOf(dto.getDirection());
        }

        Integer status = null;
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            status = Integer.valueOf(dto.getStatus());
        }

        int offset = (dto.getPageNum() - 1) * dto.getPageSize();

        var records = furtherMapper.selectPage(
                dto.getKeyword(),
                directionId,
                status,
                offset,
                dto.getPageSize()
        );

        var total = furtherMapper.count(dto.getKeyword(), directionId, status);

        return new PageResult<>(records, total);
    }

    @Override
    public Further detail(Integer id) {
        return furtherMapper.selectById(id);
    }

    @Override
    public void add(MaterialUpdateDTO dto) {
        Further f = new Further();
        f.setDirectionId(dto.getDirectionId());
        f.setTitle(dto.getTitle());
        f.setLink(dto.getLink());
        f.setDescription(dto.getDescription());
        f.setType(dto.getType());
        f.setStatus(dto.getStatus());
        furtherMapper.insert(f);
    }

    @Override
    public void update(MaterialUpdateDTO dto) {
        Further f = new Further();
        f.setId(dto.getId());
        f.setDirectionId(dto.getDirectionId());
        f.setTitle(dto.getTitle());
        f.setLink(dto.getLink());
        f.setDescription(dto.getDescription());
        f.setType(dto.getType());
        f.setStatus(dto.getStatus());
        furtherMapper.update(f);
    }

    @Override
    public void delete(Integer id) {
        furtherMapper.delete(id);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        furtherMapper.updateStatus(id, status);
    }

}
