package com.ussp.service;

import com.ussp.pojo.Further;

import java.util.List;

public interface FurtherService {

    List<Further> getListByDirection(Integer directionId);

    List<Further> adminList(Integer directionId);

    Further getById(Integer id);

    void add(Further further);

    void update(Further further);

    void delete(Integer id);
}
