package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.SysDict;

import java.util.Map;

public interface DictService {

    JSONObject add(SysDict dict);

    JSONObject edit(SysDict dict);

    JSONObject remove(SysDict dict);

    Map<String, Object> getDict(String type);
}
