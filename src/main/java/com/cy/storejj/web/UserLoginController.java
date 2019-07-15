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

    private JSONObject pageData = new JSONObject();

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String index(ModelMap model){
        pageData.put("title", "会员登录");
        model.addAttribute("page", pageData);
        return webHtml+"login";
    }

    @ResponseBody
    @RequestMapping(value = "/login/submit", method = RequestMethod.POST)
    public JSONObject login(String account, String vercode, HttpSession session){

        try {
            //userService.login(account, password, vercode, session);
            return CommonOperation.success();
        }catch (JsonException e){
            //result.put("code", e.getCode());
            //result.put("msg", e.getMsg());
            return e.toJson();
        }
    }

}
