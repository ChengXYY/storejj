package com.cy.storejj.service;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.Admin;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


public interface AdminService {
    //add user
    JSONObject add(Admin admin);
    //update user
    JSONObject edit(Admin admin);
    //delete user
    JSONObject remove(Integer id);

    Admin get(Integer id);

    Admin get(String account);

    List<Admin> getList(Map<String, Object> filter);

    Integer getCount(Map<String, Object> filter);

    JSONObject resetPassword(Integer id);

    void login(String account, String password, String vercode, HttpSession session);

    void editPassword(String oldpwd, String newpwd, String repwd, HttpSession session);

    Admin getCurrentUser();

    //群组设置为0，parentid也为0
    JSONObject resetGroups(String ids);
}
