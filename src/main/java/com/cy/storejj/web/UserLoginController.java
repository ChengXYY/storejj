package com.cy.storejj.web;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.WebConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.service.UserService;
import com.cy.storejj.utils.CommonOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserLoginController extends WebConfig {

    @Autowired
    private UserService userService;

    @RequestMapping("/userlogin")
    public String index(ModelMap model){
        return webHtml+"login";
    }

    @ResponseBody
    @RequestMapping(value = "/login/submit", method = RequestMethod.POST)
    public JSONObject login(String account, String vercode, HttpSession session){

        try {
            userService.login(account,vercode, session);
            return CommonOperation.success("登录成功！");
        }catch (JsonException e){
            //result.put("code", e.getCode());
            //result.put("msg", e.getMsg());
            return e.toJson();
        }
    }

    @RequestMapping("/sendcode")
    @ResponseBody
    public JSONObject sendCode(HttpSession session){
        String code = "1234";
        session.setAttribute(userVercode, code);
        return CommonOperation.success("短信已发送！");
    }

}
