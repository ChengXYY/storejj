package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.ProductMapper;
import com.cy.storejj.model.Product;
import com.cy.storejj.service.ProductService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {
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
            return CommonOperation.success(product.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(Product product) {
        if(!CommonOperation.checkId(product.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        //判断重复
        Product art = get(product.getCode());
        if(art!=null && art.getId()!=product.getId()) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        int rs = productMapper.updateByPrimaryKeySelective(product);
        if(rs > 0){
            return CommonOperation.success(product.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        //先删除图片
        Product p = get(id);
        CommonOperation.removeFile(p.getPic());

        int rs = productMapper.deleteByPrimaryKey(id);

        if(rs > 0){
            return CommonOperation.success(id);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
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
}
