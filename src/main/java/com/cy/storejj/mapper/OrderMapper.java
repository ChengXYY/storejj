package com.cy.storejj.mapper;

import com.cy.storejj.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    List<Order> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

    Order selectByCode(@Param("code")String code);


}