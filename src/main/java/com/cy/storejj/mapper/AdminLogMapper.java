package com.cy.storejj.mapper;

import com.cy.storejj.model.AdminLog;

import java.util.List;
import java.util.Map;

public interface AdminLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AdminLog record);

    AdminLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminLog record);

    List<AdminLog> selectByFilter(Map<String, Object> filter);

    Integer countByFilter(Map<String, Object> filter);
}