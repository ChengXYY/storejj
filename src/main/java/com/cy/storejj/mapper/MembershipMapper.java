package com.cy.storejj.mapper;

import com.cy.storejj.model.Membership;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MembershipMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Membership record);

    Membership selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Membership record);

    List<Membership> selectAll();

    Membership selectByLevel(@Param("level") Integer level);
}