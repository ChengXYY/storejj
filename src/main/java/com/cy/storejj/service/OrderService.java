package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.ProductOrder;

import java.util.List;
import java.util.Map;

public interface OrderService {

    JSONObject add(ProductOrder sell);

    JSONObject edit(ProductOrder sell);

    JSONObject remove(String code);

    JSONObject remove(Integer id);

    ProductOrder get(String code);

    ProductOrder get(Integer id);

    List<ProductOrder> getList(Map<String, Object> filter);

    Integer getCount(Map<String, Object> filter);
}
