package com.cy.storejj.mapper;

import com.cy.storejj.model.AdminGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(AdminGroup record);

    AdminGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminGroup record);

    List<AdminGroup> selectAll(@Param("parentId") Integer parentId);
}