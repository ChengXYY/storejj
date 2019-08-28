package com.cy.storejj.web;

import com.cy.storejj.config.WebConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Product;
import com.cy.storejj.model.User;
import com.cy.storejj.service.ProductService;
import com.cy.storejj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/membership")
public class UserCenterController extends WebConfig {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

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
            param.put("isDelete", 0);
            param.put("orderBy", "create_time desc");
            //获取积分商城产品
            List<Product> productList = productService.getList(param);

            model.addAttribute("myShop", productList);

            return webHtml+"user_center";

        }catch (JsonException e){
            return "/error/404";
        }
    }
}
