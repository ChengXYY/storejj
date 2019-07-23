package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.cy.storejj.mapper.AdminAuthMapper;
import com.cy.storejj.model.AdminAuth;
import com.cy.storejj.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AdminAuthMapper adminAuthMapper;

    @Override
    public List<Map<String, Object>> getAuthTree() {
        //获取所有的权限记录
        List<AdminAuth> authList = adminAuthMapper.selectAll();
        //查找根
        List<Map<String, Object>> root = new ArrayList<>();
        for(AdminAuth r : authList){
            if(StringUtils.isBlank(r.getParentCode())){
                Map<String, Object> map = new HashMap<>();
                map.put("title", r.getName());
                map.put("id", r.getCode());
                map.put("spread", "true");

                root.add(map);
            }
        }

        getTree(authList, root);

        return root;
    }

    @Override
    public String getAuthStr() {
        List<AdminAuth> authList = adminAuthMapper.selectAll();
        List<String> auth = new ArrayList<>();
        for(AdminAuth a :authList){
            auth.add(a.getCode());
        }
        return String.join(",", auth);
    }


    private void getTree(List<AdminAuth> authList, List<Map<String, Object>> root){
        for (int i = 0; i<root.size(); i++){
            if(root.get(i).get("id") == null || root.get(i).get("id").toString().isEmpty()){
                continue;
            }
            List<Map<String, Object>> children = getChildren(authList, root.get(i).get("id").toString());
            if(children.size()>0){
                getTree(authList, children);
                root.get(i).put("children", children);
            }
        }

        return;
    }

    private List<Map<String, Object>> getChildren(List<AdminAuth> authList, String parentCode){
        List<Map<String, Object>> children = new ArrayList<>();
        for(AdminAuth r : authList){

            if(StringUtils.isNotBlank(r.getParentCode()) && r.getParentCode().equals(parentCode)){
                Map<String, Object> map = new HashMap<>();
                map.put("title", r.getName());
                map.put("id", r.getCode());
                map.put("spread", "true");
                children.add(map);
            }
        }

        return children;
    }
}
