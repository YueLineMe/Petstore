package com.dao;

import com.entity.SsmCategory;
import java.util.List;

public interface SsmCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsmCategory record);

    SsmCategory selectByPrimaryKey(Integer id);

    List<SsmCategory> selectAll();

    int updateByPrimaryKey(SsmCategory record);
}