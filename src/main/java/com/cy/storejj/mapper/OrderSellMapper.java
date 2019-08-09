package com.cy.storejj.mapper;

import com.cy.storejj.model.OrderSell;

public interface OrderSellMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderSell record);

    OrderSell selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderSell record);
}