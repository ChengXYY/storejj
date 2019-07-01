package com.cy.storejj.mapper;

import com.cy.storejj.model.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    Admin selectByAccount(@Param("account") String account);

    List<Admin> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);
}