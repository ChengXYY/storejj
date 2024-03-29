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
import java.util.stream.Collectors;


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
            //图片操作
            List<ProductImages> images = product.getImages();

            images.forEach(t->{
                if(t.getSize()!=null && t.getSize() == 0){
                    //删除多余图片
                    removeFile(t.getUrl());
                }else if(StringUtils.isNotBlank(t.getName()) && StringUtils.isNotBlank(t.getUrl())){
                    t.setProductId(product.getId());
                    productMapper.insertImages(t);
                }
            });

            return success(product.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(Product product) {
        if(!checkId(product.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        //判断重复
        Product p = new Product();
        if(StringUtils.isNotBlank(product.getCode())){
            p = get(product.getCode());
            if(p!=null && p.getId()!=product.getId()) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        }else {
            if(product.getId() == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
            p = get(product.getId());
        }
        int rs = productMapper.updateByPrimaryKeySelective(product);
        if(rs > 0){
            //图片操作
            List<ProductImages> images = product.getImages();
            if(images != null && images.size()>0){
                images.forEach(t->{
                    if(t.getSize() !=null && t.getSize() == 0){
                        //删除
                        if(checkId(t.getId())){
                            deleteImages(t.getId());
                        }else {
                            removeFile(t.getUrl());
                        }
                    }else if(StringUtils.isNotBlank(t.getUrl()) && t.getId() == null){
                        //增加
                        t.setProductId(product.getId());
                        productMapper.insertImages(t);
                    }
                });
            }

            return success(product.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        /*
        //先删除图片
        Product p = get(id);
        removeFile(p.getPic());

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
                Product p = new Product();
                p.setId(Integer.parseInt(id));
                p.setIsDelete(1);
                edit(p);
                success++;
            }catch (JsonException e){
                msg += "ID"+id+"："+ e.getMsg()+ "。";
                fail++;
            }
        }
        msg = "成功下架："+success+"，失败："+fail+"。"+msg;
        if(fail > 0){
            return fail(msg);
        }else {
            return success(msg);
        }
    }

    @Override
    public JSONObject restart(String ids) {
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
                Product p = new Product();
                p.setId(Integer.parseInt(id));
                p.setIsDelete(0);
                edit(p);
                success++;
            }catch (JsonException e){
                msg += "ID"+id+"："+ e.getMsg()+ "。";
                fail++;
            }
        }
        msg = "成功删除："+success+"，失败："+fail+"。"+msg;
        if(fail > 0){
            return fail(msg);
        }else {
            return success(msg);
        }
    }

    @Override
    public JSONObject add2Shop(String ids, Integer to) {
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
            p.setIsShop(to);

            try {
                edit(p);
                success++;
            }catch (JsonException e){
                msg += "ID["+id+"]："+ e.getMsg()+ "。";
                fail++;
            }
        }
        if(to == 1)
            msg = "成功加入："+success+"，失败："+fail+"。"+msg;
        else
            msg = "成功移出："+success+"，失败："+fail+"。"+msg;
        if(fail > 0){
            return fail(msg);
        }else {
            return success(msg);
        }
    }

    @Override
    public List<Product> getList(Map<String, Object> filter) {
        List<Product> list = productMapper.selectByFilter(filter);
        if(list!=null && list.size()>0){
            for(int i=0; i<list.size(); i++){
                String url = "";
                if(list.get(i).getImages().size()>0){
                    url = list.get(i).getImages().get(0).getUrl();
                }
                list.get(i).setPic(url);
            }
        }
        return list;
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return productMapper.countByFilter(filter);
    }

    @Override
    public Product get(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);

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
            return success(images.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject deleteImages(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);

        ProductImages image = productMapper.getImage(id);
        if(image == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        //物理删除图片
        try {
            removeFile(image.getUrl());
        }catch (JsonException e){
            System.out.println(e.toJson());
        }

        int rs = productMapper.deleteImages(id);
        if(rs >= 0){
            return success(id);
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
                removeFile(r.getUrl());
            }catch (JsonException e){
                System.out.println(e.toJson());
            }
        });

        int rs = productMapper.deleteImagesByProduct(productId);
        if(rs >= 0){
            return success(productId);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public List<ProductImages> getImages(Integer productId) {
        Product product = get(productId);
        if(product == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        return product.getImages();
    }

}
