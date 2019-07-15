package com.cy.storejj.web;

import com.cy.storejj.config.WebConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.User;
import com.cy.storejj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/membership")
public class UserCenterController extends WebConfig {

    @Autowired
    private UserService userService;

    @RequestMapping("/center")
    public String userCenter(HttpSession session, ModelMap model){
        try {
            Integer id = Integer.parseInt(session.getAttribute(userId).toString());
            //用户个人信息
            User user = userService.get(id);
            model.addAttribute("user", user);

            //积分
            Integer points = user.getPoints();
            Map<String, Object> param = new HashMap<>();
            param.put("maxPoints", points);
            param.put("isShop", 1);

        }catch (JsonException e){
            return "/error/404";
        }

        return webHtml+"user_center";
    }
}
