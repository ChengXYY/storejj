package com.cy.storejj.mapper;

import com.cy.storejj.model.Product;
import com.cy.storejj.model.ProductImages;
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

    //图片操作
    int insertImages(ProductImages images);

    int batchInsertImages(@Param("images")List<ProductImages> images);

    int deleteImages(Integer id);

    int deleteImagesByProduct(@Param("productId")Integer productId);

    ProductImages getImage(Integer id);

    List<ProductImages> getImages(@Param("productId")Integer productId);
}