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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                authStr += code+",";
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

    @Override
    public List<Map<String, Object>> getGroupTree() {
        List<AdminGroup> groupList = getListAll(0);
        //查找根
        List<Map<String, Object>> root = new ArrayList<>();
    //    for(AdminGroup r : groupList){
    //        if(r.getParentId() == 0){
                Map<String, Object> map = new HashMap<>();
                map.put("title", "后台管理员组");
                map.put("id", "9999");
                map.put("spread", "true");

                root.add(map);
    //        }
    //    }

        getTree(groupList, root);

        return root;
    }

    private void getTree(List<AdminGroup> groupList , List<Map<String, Object>> root){
        for (int i = 0; i<root.size(); i++){
            if(root.get(i).get("id") == null || root.get(i).get("id").toString().isEmpty()){
                continue;
            }
            List<Map<String, Object>> children = getChildren(groupList, Integer.parseInt(root.get(i).get("id").toString()));
            if(children.size()>0){
                getTree(groupList, children);
                root.get(i).put("children", children);
            }
        }
        return;
    }

    private List<Map<String, Object>> getChildren(List<AdminGroup> groupList, Integer parentId){
        List<Map<String, Object>> children = new ArrayList<>();
        for(AdminGroup r : groupList){

            if(r.getParentId() == parentId){
                Map<String, Object> map = new HashMap<>();
                map.put("title", r.getName());
                map.put("id", r.getId());
                map.put("spread", "true");
                children.add(map);
            }
        }

        return children;
    }


}
