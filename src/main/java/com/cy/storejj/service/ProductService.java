package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.Product;

import java.util.List;
import java.util.Map;


public interface ProductService {

    JSONObject add(Product product);

    JSONObject edit(Product product);

    JSONObject remove(Integer id);

    JSONObject remove(String ids);

    List<Product> getList(Map<String, Object> filter);

    Integer getCount(Map<String, Object> filter);

    Product get(Integer id);

    Product get(String code);

}
