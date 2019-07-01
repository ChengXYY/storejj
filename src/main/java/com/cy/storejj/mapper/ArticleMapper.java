package com.cy.storejj.mapper;

import com.cy.storejj.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    List<Article> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

    Article selectByCode(@Param("code")String code);

}