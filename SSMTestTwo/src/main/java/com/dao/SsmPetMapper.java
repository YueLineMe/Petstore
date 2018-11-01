package com.dao;

import com.entity.SsmPet;
import java.util.List;

public interface SsmPetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsmPet record);

    SsmPet selectByPrimaryKey(Integer id);

    List<SsmPet> selectAll();

    int updateByPrimaryKey(SsmPet record);

    List<SsmPet> findByStatus(String status);

    int uploadImage(SsmPet record);

    int updatePetByForm(SsmPet record);
}