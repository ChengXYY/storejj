package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.SysDictMapper;
import com.cy.storejj.model.SysDict;
import com.cy.storejj.service.SysDictService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public JSONObject add(SysDict dict) {
        if(StringUtils.isBlank(dict.getType())) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        int rs =  sysDictMapper.insertSelective(dict);
        if(rs > 0){
            return CommonOperation.success(dict.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(SysDict dict) {
        if(!CommonOperation.checkId(dict.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        int rs = sysDictMapper.updateByPrimaryKeySelective(dict);
        if(rs >= 0){
            return CommonOperation.success(dict.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!CommonOperation.checkId(id)) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        int rs = sysDictMapper.deleteByPrimaryKey(id);
        if(rs >= 0){
            return CommonOperation.success(id);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public SysDict get(Integer id) {
        if(!CommonOperation.checkId(id)) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        SysDict dict = sysDictMapper.selectByPrimaryKey(id);
        if(dict == null)throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        return dict;
    }


    @Override
    public List<SysDict> getList(String type) {
        return sysDictMapper.selectByType(type);
    }
}
