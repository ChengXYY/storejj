package com.cy.storejj.mapper;

import com.cy.storejj.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    List<Product> selectByFilter(Map<String, Object> filter);

    int countByFilter(Map<String, Object> filter);

    Product selectByCode(@Param("code")String code);
}