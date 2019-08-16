package com.cy.storejj.mapper;

import com.cy.storejj.model.ProductOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ProductOrder record);

    ProductOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductOrder record);

    List<ProductOrder> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

    ProductOrder selectByCode(@Param("code")String code);


}