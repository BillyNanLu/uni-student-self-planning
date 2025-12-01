package com.ussp.service;

import com.ussp.pojo.Career;

import java.util.List;

public interface CareerService {

    List<Career> getByDirection(Integer directionId);

    Career getById(Integer id);

    void addCareer(Career career);

    void updateCareer(Career career);

    void deleteCareer(Integer id);
}
