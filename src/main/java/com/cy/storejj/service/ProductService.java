package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.Product;
import com.cy.storejj.model.ProductImages;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProductService {

    JSONObject add(Product product);

    JSONObject edit(Product product);
    //标记删除
    JSONObject remove(Integer id);
    //标记删除
    JSONObject remove(String ids);
    //删除恢复
    JSONObject restart(String ids);

    JSONObject add2Shop(String ids);

    List<Product> getList(Map<String, Object> filter);

    Integer getCount(Map<String, Object> filter);

    Product get(Integer id);

    Product get(String code);

    // Images
    JSONObject addImage(ProductImages images);

    JSONObject deleteImages(Integer id);

    JSONObject deleteImagesByProduct(Integer productId);

    List<ProductImages> getImages(Integer productId);

}
