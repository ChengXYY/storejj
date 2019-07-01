package com.cy.storejj.service;


import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {

    //add user
    JSONObject add(User user);
    //update user
    JSONObject edit(User user);
    //delete user
    JSONObject remove(Integer id);

    User get(Integer id);

    User get(String account);

    List<User> getList(Map<String, Object> filter);

    Integer getCount(Map<String, Object> filter);

    JSONObject resetPassword(Integer id);

    void login(String account, String password, String vercode, HttpSession session);

    void editPassword(String oldpwd, String newpwd, String repwd, HttpSession session); 
}
