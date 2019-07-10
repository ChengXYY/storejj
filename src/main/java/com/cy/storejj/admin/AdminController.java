package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController extends AdminConfig {

    @Autowired
    private AdminService adminService;



    @RequestMapping("/editpwd")
    public String editPassword(){
        return adminHtml +"editpwd";
    }

    @ResponseBody
    @RequestMapping("/editpwd/submit")
    public JSONObject editPassword(String oldpwd, String newpwd, String repwd, HttpSession session){

        JSONObject result = new JSONObject();
        try {
            adminService.editPassword(oldpwd, newpwd, repwd, session);
            result.put("code", 1);
            result.put("msg", "修改成功");
            return result;
        }catch (JsonException e) {
            //result.put("code", e.getCode());
            //result.put("msg", e.getMsg());
            return e.toJson();
        }
    }
}
