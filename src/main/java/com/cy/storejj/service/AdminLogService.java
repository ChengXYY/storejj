package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.AdminLog;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface AdminLogService {
    //add
    JSONObject add(HttpSession session, String content);

    //get list
    List<AdminLog> getList(Map<String, Object> filter);

    //get count
    Integer getCount(Map<String, Object> filter);
}
