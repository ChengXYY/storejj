package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.ClientConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.User;
import com.cy.storejj.service.UserService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ClientConfig implements UserService{
    @Override
    public JSONObject add(User user) {
        if(StringUtils.isBlank(user.getAccount())) throw JsonException.newInstance(ErrorCodes.ACCOUNT_NOT_EMPTY);
        if(StringUtils.isBlank(user.getPassword()))throw JsonException.newInstance(ErrorCodes.PASSWORD_NOT_EMPTY);
        if(StringUtils.isBlank(user.getMobile()))throw JsonException.newInstance(ErrorCodes.MOBILE_NOT_EMPTY);

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
        if(session.getAttribute(userSession) != null) return;
        if(account.isEmpty() || password.isEmpty() || vercode.isEmpty()) throw  JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        //验证码
        if(session.getAttribute(verCode).toString().isEmpty()) throw JsonException.newInstance(ErrorCodes.VERCODE_NOT_EMPTY);
        if(!session.getAttribute(verCode).toString().equals(vercode)) throw JsonException.newInstance(ErrorCodes.VERCODE_IS_WRONG);

        User user = get(account);
        String varifyPwd = CommonOperation.encodeStr(password, user.getSalt());
        if(!varifyPwd.equals(user.getPassword())) throw JsonException.newInstance(ErrorCodes.PASSWORD_IS_WRONG);
        String sessionStr = CommonOperation.encodeStr(user.getId().toString(), user.getAccount());
        session.setAttribute(userSession, sessionStr);
        session.setAttribute(userAccount, user.getAccount());
        session.setAttribute(userLevel, user.getLevel());
        session.setAttribute(userId, user.getId());
    }

    @Override
    public void editPassword(String oldpwd, String newpwd, String repwd, HttpSession session) {

    }
}
