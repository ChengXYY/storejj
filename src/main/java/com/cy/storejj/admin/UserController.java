package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.User;
import com.cy.storejj.service.UserService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@Permission("1004")
public class UserController extends AdminConfig {

    @Autowired
    private UserService userService;

    @Permission("2141")
    @RequestMapping("/list")
    public String list(@RequestParam Map<String,Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();
        if(param.get("name")!=null && StringUtils.isNotBlank(param.get("name").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "name", param.get("name").toString());
        }
        param.put("currentUrl", currentUrl);

        int totalCount = userService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<User> list = userService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",editPageTitle+userMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "user");
        model.addAttribute("LeftMenuFlag", "userinfo");
        return adminHtml +"user_list";
    }

    @Permission("2142")
    @RequestMapping("/add")
    public String add(){
        return adminHtml +"user_add";
    }

    @Permission("2142")
    @RequestMapping("/add/submit")
    public JSONObject add(@RequestParam Map<String, Object> param){
        return null;
    }

    @Permission("2147")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public JSONObject getUserInfo(@RequestParam(value = "account")String account){
        try {
            User user = userService.get(account);
            //脱敏处理
            String name = CommonOperation.maskName(user.getName());
            String email = CommonOperation.maskEmail(user.getEmail());
            String mobile = CommonOperation.maskMobile(user.getMobile());
            user.setEmail(email);
            user.setMobile(mobile);
            user.setName(name);
            user.setId(null);
            return CommonOperation.success(user);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2148")
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    public JSONObject edit(@RequestParam Map<String, Object> param){
        return null;
    }

    @Permission("2143")
    @RequestMapping("/points")
    public String getUserPoints(ModelMap model){
        model.addAttribute("pageTitle",editPageTitle+userMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "user");
        model.addAttribute("LeftMenuFlag", "points");
        return adminHtml +"user_points";
    }

    @Permission("2144")
    @RequestMapping(value = "/points/submit", method = RequestMethod.POST)
    public JSONObject editUserPoints(@RequestParam(value = "account")String account,
                                     @RequestParam(value = "points")Integer points,
                                     @RequestParam(value = "type")Integer type) {
        try {
            User user = userService.get(account);
            //参数检查
            if(points<0){
                return CommonOperation.fail("积分必须为正数");
            }
            int oldPoints = user.getPoints();
            if(type == 0){
                //消费积分
                points = oldPoints-points;
                if(points<0){
                    return CommonOperation.fail("积分不足");
                }
            }else if(type == 1){
                points = oldPoints+points;
            }else {
                return CommonOperation.fail();
            }

            User newUser = new User();
            newUser.setId(user.getId());
            newUser.setPoints(user.getPoints());
            return userService.edit(newUser);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2145")
    @RequestMapping("/level")
    public String getUserLevel(ModelMap model){
        model.addAttribute("pageTitle",editPageTitle+userMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "user");
        model.addAttribute("LeftMenuFlag", "level");
        return adminHtml +"user_level";
    }

    @Permission("2146")
    @RequestMapping("/level/submit")
    public JSONObject editUserLevel(@RequestParam(value = "account")String account,
                                    @RequestParam(value = "level")Integer level,
                                    @RequestParam(value = "type")Integer type) {
        try {
            User user = userService.get(account);
            //参数检查
            if(level<0){
                return CommonOperation.fail("等级必须为正数");
            }
            int oldLevel = user.getLevel();
            if(type == 0){
                //消费积分
                level = oldLevel-level;
                if(level<0){
                    return CommonOperation.fail("等级已为0");
                }
            }else if(type == 1){
                level = oldLevel+level;
            }else {
                return CommonOperation.fail();
            }

            User newUser = new User();
            newUser.setId(user.getId());
            newUser.setLevel(user.getLevel());
            return userService.edit(newUser);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
