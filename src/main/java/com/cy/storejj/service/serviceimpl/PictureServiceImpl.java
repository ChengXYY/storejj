package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.PictureMapper;
import com.cy.storejj.model.Picture;
import com.cy.storejj.service.PictureService;
import com.cy.storejj.utils.CommonOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PictureServiceImpl extends AdminConfig implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;


    @Override
    public JSONObject add(Picture picture) {
        if(picture.getCode().isEmpty())throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        //判断重复
        Picture pic = get(picture.getCode());
        if(pic!=null) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        int rs = pictureMapper.insertSelective(picture);
        if(rs > 0){
            return success(picture.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(Picture picture) {
        if(!checkId(picture.getId())) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        if(picture.getCode().isEmpty())throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        //判断重复
        Picture pic = get(picture.getCode());
        if(pic!=null && pic.getId()!=picture.getId()) throw JsonException.newInstance(ErrorCodes.CODE_REPEATED);
        int rs = pictureMapper.updateByPrimaryKeySelective(picture);
        if(rs > 0){
            return success(picture.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        //获取picture
        Picture pic = get(id);
        if(pic == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        try {
            removeFile(pic.getUrl());
        }catch (JsonException e){
            System.out.println(e.toJson());
        }

        int rs = pictureMapper.deleteByPrimaryKey(id);

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
    public List<Picture> getList(Map<String, Object> filter) {
        return pictureMapper.selectByFilter(filter);
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return pictureMapper.countByFilter(filter);
    }

    @Override
    public Picture get(Integer id) {
        if(!checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);

        return pictureMapper.selectByPrimaryKey(id);
    }

    @Override
    public Picture get(String code) {
        if(code == null || code.isEmpty())throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);

        return pictureMapper.selectByCode(code);
    }
}
