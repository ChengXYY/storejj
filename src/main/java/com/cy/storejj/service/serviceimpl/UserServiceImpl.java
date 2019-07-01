package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.model.User;
import com.cy.storejj.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public JSONObject add(User user) {
        return null;
    }

    @Override
    public JSONObject edit(User user) {
        return null;
    }

    @Override
    public JSONObject remove(Integer id) {
        return null;
    }

    @Override
    public User get(Integer id) {
        return null;
    }

    @Override
    public User get(String account) {
        return null;
    }

    @Override
    public List<User> getList(Map<String, Object> filter) {
        return null;
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return null;
    }

    @Override
    public JSONObject resetPassword(Integer id) {
        return null;
    }

    @Override
    public void login(String account, String password, String vercode, HttpSession session) {

    }

    @Override
    public void editPassword(String oldpwd, String newpwd, String repwd, HttpSession session) {

    }
}
