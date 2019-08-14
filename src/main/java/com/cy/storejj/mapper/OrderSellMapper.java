package com.cy.storejj.mapper;

import com.cy.storejj.model.OrderSell;

import java.util.List;
import java.util.Map;

public interface OrderSellMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderSell record);

    OrderSell selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderSell record);

    List<OrderSell> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

}