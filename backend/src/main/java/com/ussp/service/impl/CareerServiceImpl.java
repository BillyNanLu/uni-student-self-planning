package com.ussp.service.impl;

import com.ussp.dto.CareerQueryDTO;
import com.ussp.mapper.CareerMapper;
import com.ussp.pojo.Career;
import com.ussp.pojo.Direction;
import com.ussp.pojo.PageResult;
import com.ussp.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public List<Direction> getDirectionList() {
        return careerMapper.getDirectionList();
    }

    @Override
    public PageResult<Career> getCareerList(CareerQueryDTO query) {
        // 统计总数
        Integer total = careerMapper.countCareers(query);

        if (total == null || total == 0) {
            return new PageResult<>(Collections.emptyList(), 0);
        }

        // 查询列表数据
        List<Career> list = careerMapper.getCareerList(query);

        return new PageResult<>(list, total);
    }

    @Override
    public Career getCareerDetail(Integer id) {
        return careerMapper.getCareerDetail(id);
    }

    @Override
    public void addCareer(Career career) {
        careerMapper.insertCareer(career);
    }

    @Override
    public void updateCareer(Career career) {
        careerMapper.updateCareer(career);
    }

    @Override
    public void updateCareerStatus(Integer id, Integer status) {
        careerMapper.updateCareerStatus(id, status);
    }

    @Override
    public void deleteCareer(Integer id) {
        careerMapper.deleteCareer(id);
    }


}
