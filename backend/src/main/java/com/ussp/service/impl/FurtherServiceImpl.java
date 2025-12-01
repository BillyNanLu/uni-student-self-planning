package com.ussp.service.impl;

import com.ussp.mapper.FurtherMapper;
import com.ussp.pojo.Further;
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
    public List<Further> adminList(Integer directionId) {
        return furtherMapper.adminList(directionId);
    }

    @Override
    public Further getById(Integer id) {
        return furtherMapper.getById(id);
    }

    @Override
    public void add(Further further) {
        furtherMapper.insert(further);
    }

    @Override
    public void update(Further further) {
        furtherMapper.update(further);
    }

    @Override
    public void delete(Integer id) {
        furtherMapper.delete(id);
    }
}
