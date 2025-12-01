package com.ussp.service.impl;

import com.ussp.mapper.CareerMapper;
import com.ussp.pojo.Career;
import com.ussp.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerServiceImpl implements CareerService {

    @Autowired
    private CareerMapper careerMapper;

    @Override
    public List<Career> getByDirection(Integer directionId) {
        return careerMapper.findByDirection(directionId);
    }

    @Override
    public Career getById(Integer id) {
        return careerMapper.findById(id);
    }

    @Override
    public void addCareer(Career career) {
        careerMapper.insert(career);
    }

    @Override
    public void updateCareer(Career career) {
        careerMapper.update(career);
    }

    @Override
    public void deleteCareer(Integer id) {
        careerMapper.delete(id);
    }
}
