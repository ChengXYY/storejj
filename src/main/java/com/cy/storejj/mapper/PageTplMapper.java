package com.cy.storejj.mapper;

import com.cy.storejj.model.PageTpl;

import java.util.List;
import java.util.Map;

public interface PageTplMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PageTpl record);

    PageTpl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PageTpl record);

    List<PageTpl> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

    List<PageTpl> selectListAll();

}