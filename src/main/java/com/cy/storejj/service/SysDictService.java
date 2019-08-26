package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.SysDict;
import org.hibernate.validator.constraints.EAN;

import java.util.List;
import java.util.Map;

public interface SysDictService {

    JSONObject add(SysDict dict);

    JSONObject edit(SysDict dict);

    JSONObject remove(Integer id);

    SysDict get(Integer id);

    List<SysDict> getList(String type);
}
