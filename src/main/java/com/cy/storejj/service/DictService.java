package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.SysDict;


import java.util.List;
import java.util.Map;

public interface DictService {

    JSONObject add(SysDict dict);

    JSONObject edit(SysDict dict);

    JSONObject remove(SysDict dict);

    List<Map<String, Object>> getList(Map<String, Object> filter);
}
