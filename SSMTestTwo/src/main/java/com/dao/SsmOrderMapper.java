package com.dao;

import com.entity.SsmOrder;
import java.util.List;
import java.util.Map;

public interface SsmOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsmOrder record);

    SsmOrder selectByPrimaryKey(Integer id);

    List<SsmOrder> selectAll();

    int updateByPrimaryKey(SsmOrder record);

    Map<String,Integer> selectInventoriesByStatus();
}