package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.mapper.SysDictMapper;
import com.cy.storejj.model.SysDict;
import com.cy.storejj.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictServiceImpl implements DictService {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public JSONObject add(SysDict dict) {
        return null;
    }

    @Override
    public JSONObject edit(SysDict dict) {
        return null;
    }

    @Override
    public JSONObject remove(SysDict dict) {
        return null;
    }

    @Override
    public Map<String, Object> getDict(String type) {
        List<Map<String, Object>> dictList = sysDictMapper.selectByType(type);
        Map<String, Object> map = new HashMap<>();
        dictList.forEach(r->{
            map.put(r.get("value").toString(), r.get("text"));
        });
        return map;
    }
}
