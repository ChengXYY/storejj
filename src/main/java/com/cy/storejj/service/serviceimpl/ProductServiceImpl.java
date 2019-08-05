package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.ProductMapper;
import com.cy.storejj.model.Product;
import com.cy.storejj.model.ProductImages;
import com.cy.storejj.service.ProductService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl extends AdminConfig implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public JSONObject add(Product product) {
        if(StringUtils.isBlank(product.getCode()))throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        //判断重复
        Product art = get(product.getCode());
        if(art!=null) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        int rs = productMapper.insertSelective(product);
        if(rs > 0){
            //插入图片
            List<ProductImages> images = product.getImages();
            if(images.size()>0){
                images.forEach(r->{
                    r.setProductId(product.getId());
                    productMapper.insertImages(r);
                });
            }
            return CommonOperation.success(product.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(Product product) {
        if(!CommonOperation.checkId(product.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        //判断重复
        Product p = get(product.getCode());
        if(p!=null && p.getId()!=product.getId()) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        int rs = productMapper.updateByPrimaryKeySelective(product);
        if(rs > 0){
            //图片操作
            List<ProductImages> images = p.getImages();

            return CommonOperation.success(product.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        /*
        //先删除图片
        Product p = get(id);
        CommonOperation.removeFile(p.getPic());

        int rs = productMapper.deleteByPrimaryKey(id);
        */
        Product p = new Product();
        p.setId(id);
        p.setIsDelete(1);

        return edit(p);
    }

    @Override
    public JSONObject remove(String ids) {
        if(ids == null || ids.isEmpty()) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        ids = ids.replace(" ", "");
        String[] idArr = ids.split(",");
        String msg = "";
        int success = 0;
        int count = 0;
        int fail = 0;
        for(String id : idArr){
            count ++;
            try {
                remove(Integer.parseInt(id));
                success++;
            }catch (JsonException e){
                msg += "ID"+id+"："+ e.getMsg()+ "。";
                fail++;
            }
        }
        msg = "成功删除："+success+"，失败："+fail+"。"+msg;
        if(fail > 0){
            return CommonOperation.fail(msg);
        }else {
            return CommonOperation.success(msg);
        }
    }

    @Override
    public JSONObject add2Shop(String ids) {
        if(ids == null || ids.isEmpty()) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        ids = ids.replace(" ", "");
        String[] idArr = ids.split(",");
        String msg = "";
        int success = 0;
        int count = 0;
        int fail = 0;
        for(String id : idArr){
            count ++;
            Product p = new Product();

            p.setId(Integer.parseInt(id));
            p.setIsShop(1);

            try {
                edit(p);
                success++;
            }catch (JsonException e){
                msg += "ID"+id+"："+ e.getMsg()+ "。";
                fail++;
            }
        }
        msg = "成功加入："+success+"，失败："+fail+"。"+msg;
        if(fail > 0){
            return CommonOperation.fail(msg);
        }else {
            return CommonOperation.success(msg);
        }
    }

    @Override
    public List<Product> getList(Map<String, Object> filter) {
        return productMapper.selectByFilter(filter);
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return productMapper.countByFilter(filter);
    }

    @Override
    public Product get(Integer id) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);

        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public Product get(String code) {
        if(code == null || code.isEmpty())throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);

        return productMapper.selectByCode(code);
    }

    @Override
    public JSONObject addImage(ProductImages images) {
        if(StringUtils.isBlank(images.getUrl()))throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        int rs = productMapper.insertImages(images);

        if(rs > 0){
            return CommonOperation.success(images.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject deleteImages(Integer id) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);

        ProductImages image = productMapper.getImage(id);
        if(image == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        //物理删除图片
        try {
            CommonOperation.removeFile(image.getUrl());
        }catch (JsonException e){
            System.out.println(e.toJson());
        }

        int rs = productMapper.deleteImagesByProduct(id);
        if(rs >= 0){
            return CommonOperation.success(id);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject deleteImagesByProduct(Integer productId) {
        List<ProductImages> images = productMapper.getImages(productId);

        if(images.size() < 1) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        images.forEach(r->{
            //物理删除图片
            try {
                CommonOperation.removeFile(r.getUrl());
            }catch (JsonException e){
                System.out.println(e.toJson());
            }
        });

        int rs = productMapper.deleteImagesByProduct(productId);
        if(rs >= 0){
            return CommonOperation.success(productId);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public List<ProductImages> getImages(Integer productId) {
        Product product = get(productId);
        if(product == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        return productMapper.getImages(productId);
    }
}
