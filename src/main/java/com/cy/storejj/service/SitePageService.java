package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.SitePage;

import java.util.List;
import java.util.Map;


public interface SitePageService {

    JSONObject add(SitePage sitepage);

    JSONObject edit(SitePage sitepage);

    JSONObject remove(Integer id);

    JSONObject remove(String ids);

    List<SitePage> getList(Map<String, Object> filter);

    int getCount(Map<String, Object> filter);

    SitePage get(Integer id);

    SitePage get(String code);
}
