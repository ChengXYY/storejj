package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.config.WebConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.UserMapper;
import com.cy.storejj.model.User;
import com.cy.storejj.service.UserService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl extends WebConfig implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject add(User user) {
        if(StringUtils.isBlank(user.getAccount())) throw JsonException.newInstance(ErrorCodes.ACCOUNT_NOT_EMPTY);
        if(StringUtils.isBlank(user.getPassword()))throw JsonException.newInstance(ErrorCodes.PASSWORD_NOT_EMPTY);
        if(StringUtils.isBlank(user.getMobile()))throw JsonException.newInstance(ErrorCodes.MOBILE_NOT_EMPTY);

        int rs = userMapper.insertSelective(user);
        if(rs > 0){
            return CommonOperation.success(user.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject edit(User user) {
        if(CommonOperation.checkId(user.getId()))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        int rs = userMapper.updateByPrimaryKeySelective(user);
        if(rs >= 0){
            return CommonOperation.success(user.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public JSONObject remove(Integer id) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        int rs = userMapper.deleteByPrimaryKey(id);

        if(rs > 0){
            return CommonOperation.success(id);
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

    @Override
    public User get(Integer id) {
        if(!CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User get(String account) {
        if(StringUtils.isBlank(account))throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        User user = userMapper.selectByAccount(account);

        return user;
    }

    @Override
    public List<User> getList(Map<String, Object> filter) {
        List<User> userList = userMapper.selectByFilter(filter);
        if(userList.size()>0){
            for(int i=0; i< userList.size(); i++){
                if(StringUtils.isNotBlank(userList.get(i).getMobile())){
                    String mobile = CommonOperation.maskMobile(userList.get(i).getMobile());
                    userList.get(i).setMobile(mobile);
                }

                if(StringUtils.isNotBlank(userList.get(i).getEmail())){
                    String email = CommonOperation.maskEmail(userList.get(i).getEmail());
                    userList.get(i).setEmail(email);
                }
            }
        }
        return userList;
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return userMapper.countByFilter(filter);
    }

    @Override
    public JSONObject resetPassword(Integer id) {
        return null;
    }

    @Override
    public JSONObject login(String account, String vercode, HttpSession session) {
        if(session.getAttribute(userSession) != null) return CommonOperation.success("已登录");
        //验证码
        if(session.getAttribute(verCode).toString().isEmpty()) throw JsonException.newInstance(ErrorCodes.VERCODE_NOT_EMPTY);
        if(!session.getAttribute(verCode).toString().equals(vercode)) throw JsonException.newInstance(ErrorCodes.VERCODE_IS_WRONG);

        User user = get(account);
        if(user == null) throw JsonException.newInstance(ErrorCodes.ITEM_NOT_EXIST);

        session.setAttribute(userAccount, user.getAccount());
        session.setAttribute(userLevel, user.getLevel());
        session.setAttribute(userId, user.getId());
        return CommonOperation.success("登录成功！");
    }

    @Override
    public JSONObject editPassword(String oldpwd, String newpwd, String repwd, HttpSession session) {
        return null;
    }

    @Override
    public JSONObject register(String account, String vercode, HttpSession session, String createBy) {


        if(StringUtils.isBlank(account) || StringUtils.isBlank(vercode))throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        //验证码
        if(session.getAttribute(verCode).toString().isEmpty()) throw JsonException.newInstance(ErrorCodes.VERCODE_NOT_EMPTY);
        if(!session.getAttribute(verCode).toString().equals(vercode)) throw JsonException.newInstance(ErrorCodes.VERCODE_IS_WRONG);

        User u = get(account);
        if(u != null)
            throw JsonException.newInstance(ErrorCodes.ITEM_REPEATED);


        String userCode = "DP"+System.currentTimeMillis();

        User user = new User();
        user.setMobile(account);
        user.setAccount(account);
        user.setNickname(userCode);
        user.setName(userCode);
        user.setUserCode(userCode);
        user.setCreateBy(createBy);

        int rs = userMapper.insertSelective(user);
        if(rs > 0){
            return CommonOperation.success(user.getId());
        }else {
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
        }
    }

}
