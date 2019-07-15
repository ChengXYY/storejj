package com.cy.storejj.mapper;

import com.cy.storejj.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDict record);

    List<Map<String, Object>> selectByType(@Param(value = "type")String type);
}