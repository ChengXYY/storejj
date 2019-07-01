package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.AdminGroup;

import java.util.List;

public interface AdminGroupService {
    //list all
    List<AdminGroup> getListAll(Integer parentid);

    //count all
    Integer countAll(Integer parentid);

    //add
    JSONObject add(AdminGroup admingroup);

    //edit
    JSONObject edit(AdminGroup admingroup);

    //delete
    JSONObject remove(Integer id);

    //get
    AdminGroup get(Integer id);

    //auth
    JSONObject changeAuth(Integer id, String[] auths);

}
