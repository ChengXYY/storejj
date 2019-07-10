package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.PageTpl;

import java.util.List;
import java.util.Map;


public interface PageTplService {
    JSONObject add(PageTpl pagetpl);

    JSONObject edit(PageTpl pagetpl);

    JSONObject remove(Integer id);

    JSONObject remove(String ids);

    int getCount(Map<String, Object> filter);

    List<PageTpl> getList(Map<String, Object> filter);

    JSONObject get(Integer id);

    List<PageTpl> getSelectList();
}
