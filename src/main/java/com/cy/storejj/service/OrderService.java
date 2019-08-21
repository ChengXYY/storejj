package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    JSONObject add(Order sell);

    JSONObject edit(Order sell);

    JSONObject remove(String code);

    JSONObject remove(Integer id);

    Order get(String code);

    Order get(Integer id);

    List<Order> getList(Map<String, Object> filter);

    Integer getCount(Map<String, Object> filter);
}
