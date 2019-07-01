package com.cy.storejj.mapper;

import com.cy.storejj.model.SitePage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SitePageMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SitePage record);

    SitePage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SitePage record);

    List<SitePage> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

    SitePage selectByCode(@Param("code")String code);

}