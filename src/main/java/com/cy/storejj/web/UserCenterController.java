package com.cy.storejj.web;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.WebConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Membership;
import com.cy.storejj.model.Order;
import com.cy.storejj.model.Product;
import com.cy.storejj.model.User;
import com.cy.storejj.service.MembershipService;
import com.cy.storejj.service.ProductService;
import com.cy.storejj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/usercenter")
@Controller
public class UserCenterController extends WebConfig {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MembershipService membershipService;

    @RequestMapping(value = {"", "/", "/index"})
    public String userCenter(HttpSession session, ModelMap model){

        model.addAttribute("pageTitle", "个人中心 - "+systemTitle);
        model.addAttribute("topFlag", "usercenter");

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

            return webHtml+"usercenter";

        }catch (JsonException e){
            return "/error/404";
        }
    }

    @RequestMapping("/edit")
    public String userEdit(HttpSession session, ModelMap modelMap){

        modelMap.addAttribute("pageTitle", "个人中心 - "+systemTitle);
        modelMap.addAttribute("topFlag", "usercenter");

        String account = session.getAttribute(userAccount).toString();
        try {
            User user = userService.get(account);
            modelMap.addAttribute("user", user);
            return webHtml+"usercenter_edit";
        }catch (JsonException e){
            return "/error/404";
        }

    }

    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject userEdit(User user, HttpSession session){
        String account = session.getAttribute(userAccount).toString();
        try {
            User u = userService.get(account);
            User editUser = new User();
            editUser.setName(user.getName());
            editUser.setNickname(user.getNickname());
            editUser.setEmail(user.getEmail());
            editUser.setMobile(user.getMobile());
            editUser.setId(u.getId());
            return userService.edit(editUser);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @RequestMapping("/exchange")
    public String exchange(@RequestParam("code")String code, HttpSession session, ModelMap modelMap){

        modelMap.addAttribute("pageTitle", "个人中心 - "+systemTitle);
        modelMap.addAttribute("topFlag", "usercenter");

        try {
            Product product = productService.get(code);
            modelMap.addAttribute("product", product);
            String account = session.getAttribute(userAccount).toString();
            User user = userService.get(account);
            modelMap.addAttribute("user", user);
        }catch (JsonException e){
            return "/error/404";
        }

        return webHtml+"usercenter_exchange";
    }

    @RequestMapping(value = "/exchange/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject exchange(Order order, HttpSession session){
        String account = session.getAttribute(userAccount).toString();
        return null;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(userSession);
        session.removeAttribute(userAccount);
        session.removeAttribute(userId);
        session.removeAttribute(userLevel);
        return "redirect:/userlogin";
    }

}
