package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.Suggestion;

import java.util.List;
import java.util.Map;

public interface SuggestionService {
    JSONObject add(Suggestion suggestion);

    JSONObject edit(Suggestion suggestion);

    JSONObject remove(Integer id);

    JSONObject remove(String ids);

    List<Suggestion> getList(Map<String, Object> filter);

    Integer getCount(Map<String, Object> filter);

    Suggestion get(Integer id);
}
