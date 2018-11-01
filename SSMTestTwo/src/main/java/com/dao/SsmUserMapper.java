package com.dao;

import com.entity.SsmUser;
import java.util.List;

public interface SsmUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsmUser record);

    SsmUser selectByPrimaryKey(Integer id);

    List<SsmUser> selectAll();

    int updateByPrimaryKey(SsmUser record);

    int selectLogin(SsmUser record);

    int updateStatus(SsmUser record);

    SsmUser selectByUserName(String username);
}