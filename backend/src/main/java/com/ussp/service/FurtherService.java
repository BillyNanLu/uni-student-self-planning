package com.ussp.service;

import com.ussp.dto.MaterialQueryDTO;
import com.ussp.dto.MaterialUpdateDTO;
import com.ussp.pojo.Further;
import com.ussp.pojo.PageResult;

import java.util.List;

public interface FurtherService {

    List<Further> getListByDirection(Integer directionId);

    PageResult<Further> page(MaterialQueryDTO dto);

    Further detail(Integer id);

    void add(MaterialUpdateDTO dto);

    void update(MaterialUpdateDTO dto);

    void delete(Integer id);

    void updateStatus(Integer id, Integer status);
}
