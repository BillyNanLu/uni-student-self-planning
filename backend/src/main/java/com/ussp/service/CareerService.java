package com.ussp.service;

import com.ussp.dto.CareerQueryDTO;
import com.ussp.pojo.Career;
import com.ussp.pojo.Direction;
import com.ussp.pojo.PageResult;

import java.util.List;

public interface CareerService {

    List<Career> getByDirection(Integer directionId);

    List<Direction> getDirectionList();

    PageResult<Career> getCareerList(CareerQueryDTO query);

    Career getCareerDetail(Integer id);

    void addCareer(Career career);

    void updateCareer(Career career);

    void updateCareerStatus(Integer id, Integer status);

    void deleteCareer(Integer id);

}
