package com.cy.storejj.mapper;

import com.cy.storejj.model.Suggestion;

import java.util.List;
import java.util.Map;

public interface SuggestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Suggestion record);

    Suggestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Suggestion record);

    int countByFilter(Map<String, Object> filter);

    List<Suggestion> selectByFilter(Map<String, Object> filter);
}