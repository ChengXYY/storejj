package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.CategoryMapper;
import com.cy.storejj.model.Category;
import com.cy.storejj.service.CategoryService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CategoryServiceImpl extends AdminConfig implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public JSONObject add(Category category) {
        if(StringUtils.isBlank(category.getCode()) || StringUtils.isBlank(category.getName()))throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        //判断重复
        Category category1 = get(category.getCode());
        if(category1 != null)throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        int rs = categoryMapper.insertSelective(category);
        if(rs > 0){
            return success(category.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(Category category) {
        if(!checkId(category.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        if(StringUtils.isNotBlank(category.getCode())){
            Category category1 = get(category.getCode());
            if(category1.getId()!=category.getId())throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        }
        int rs = categoryMapper.updateByPrimaryKeySelective(category);
        if(rs >= 0){
            return success(category.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        //删除图片
        Category c = get(id);
        removeFile(c.getPic());
        int rs = categoryMapper.deleteByPrimaryKey(id);

        if(rs > 0){
            return success(id);
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
            return fail(msg);
        }else {
            return success(msg);
        }
    }

    @Override
    public List<Category> getList(Map<String, Object> filter) {
        return categoryMapper.selectByFilter(filter);
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return categoryMapper.countByFilter(filter);
    }

    @Override
    public Category get(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Category get(String code) {
        if(StringUtils.isBlank(code))throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        return categoryMapper.selectByCode(code);
    }
}
