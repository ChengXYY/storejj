package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.AdminMapper;
import com.cy.storejj.mapper.AdminGroupMapper;
import com.cy.storejj.model.Admin;
import com.cy.storejj.model.AdminGroup;
import com.cy.storejj.service.AdminGroupService;
import com.cy.storejj.utils.CommonOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminGroupServiceImpl implements AdminGroupService {

    @Autowired
    private AdminGroupMapper admingroupMapper;

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public List<AdminGroup> getListAll(Integer parentid) {
        return admingroupMapper.selectAll(parentid);
    }

    @Override
    public Integer countAll(Integer parentid) {
        return getListAll(parentid).size();
    }

    @Override
    public JSONObject add(AdminGroup admingroup) {
        if(admingroup.getName().isEmpty() || !CommonOperation.checkId(admingroup.getSort())) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        int rs = admingroupMapper.insertSelective(admingroup);
        if(rs>0) {
            return CommonOperation.success(admingroup.getId());
        }else
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
    }

    @Override
    public JSONObject edit(AdminGroup admingroup) {
        if(admingroup.getId()==null || admingroup.getId()<1 )throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);

        int rs = admingroupMapper.updateByPrimaryKeySelective(admingroup);
        if(rs > 0){
            return CommonOperation.success(admingroup.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        // 判断该组是否存在
        AdminGroup group = get(id);
        if(group == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        // 先判断组内是否有成员
        Map<String, Object> filter = new HashMap<>();
        filter.put("groupid", id);
        List<Admin> members = adminMapper.selectByFilter(filter);
        if(members.size() >0) throw JsonException.newInstance(ErrorCodes.GROUP_NOT_EMPTY);

        int rs = admingroupMapper.deleteByPrimaryKey(id);

        if(rs > 0){
            return CommonOperation.success(id);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public AdminGroup get(Integer id) {
        if(!CommonOperation.checkId(id)) throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        AdminGroup admingroup = admingroupMapper.selectByPrimaryKey(id);
        if(admingroup == null)throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);
        return admingroup;
    }

    @Override
    public JSONObject changeAuth(Integer id, String[] auths) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        AdminGroup admingroup = get(id);

        String authStr = "";
        if(auths.length>0){
            for (String code : auths){
                authStr += code+"|";
            }
            authStr.substring(0,authStr.length()-1);
        }
        admingroup.setAuth(authStr);
        int rs = admingroupMapper.updateByPrimaryKeySelective(admingroup);
        if(rs > 0){
            return CommonOperation.success(id);
        }else
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
    }


}
