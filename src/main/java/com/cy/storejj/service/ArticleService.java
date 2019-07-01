package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    JSONObject add(Article article);

    JSONObject edit(Article article);

    JSONObject remove(Integer id);

    JSONObject remove(String ids);

    List<Article> getList(Map<String, Object> filter);

    Integer getCount(Map<String, Object> filter);

    Article get(Integer id);

    Article get(String code);

}
