package com.dao;

import com.entity.SsmLog;
import java.util.List;

public interface SsmLogMapper {
    int deleteByPrimaryKey(Integer logid);

    int insert(SsmLog record);

    SsmLog selectByPrimaryKey(Integer logid);

    List<SsmLog> selectAll();

    int updateByPrimaryKey(SsmLog record);
}