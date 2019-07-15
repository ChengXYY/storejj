package com.cy.storejj.mapper;

import com.cy.storejj.model.AdminAuth;

import java.util.List;

public interface AdminAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AdminAuth record);

    AdminAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminAuth record);

    List<AdminAuth> selectAll();
}