package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.User;
import com.cy.storejj.service.UserService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")

public class UserController extends AdminConfig {

    @Autowired
    private UserService userService;

    @Permission("4100")
    @RequestMapping("/list")
    public String list(@RequestParam Map<String,Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();
        currentUrl = handleParam(param, currentUrl);
        param.put("currentUrl", currentUrl);

        int totalCount = userService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<User> list = userService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+userMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "user");
        model.addAttribute("LeftMenuFlag", "userinfo");
        return adminHtml +"user_list";
    }

    @Permission("4102")
    @RequestMapping("/register")
    public String add(){
        return adminHtml +"user_register";
    }

    //获取验证码
    @Permission("4102")
    @ResponseBody
    @RequestMapping("/getcode")
    public JSONObject getCode(HttpSession session){
        String code = "1234";
        session.setAttribute(userVercode, code);
        return CommonOperation.success();
    }
    //

    @Permission("4102")
    @RequestMapping("/register/submit")
    @ResponseBody
    public JSONObject register(@RequestParam Map<String, Object> param, HttpSession session){

        try {
            //参数只有mobile 和 vercode
            String account = param.get("mobile").toString();
            String sVercode = param.get("vercode").toString();
            return userService.register(account, sVercode, session, session.getAttribute(adminAccount).toString());
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("4103")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam("id")Integer id, ModelMap model){
        try {
            User user = userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle",editPageTitle+userMenuTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "user");
            model.addAttribute("LeftMenuFlag", "userinfo");
            return adminHtml+"user_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }


    @Permission("4103")
    @ResponseBody
    @RequestMapping(value = "/edit/{type}", method = RequestMethod.POST)
    public JSONObject edit(User user, @PathVariable("type")String type, HttpSession session){
        if(!checkEditParam(type, user)){
            return CommonOperation.fail("编辑内容不合法");
        }

        try {
            user.setCreateBy(session.getAttribute(adminAccount).toString());
            return userService.edit(user);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    private boolean checkEditParam(String type, User user){
        if(!type.equals("basic") && !type.equals("account")) return false;
        switch (type){
            case "basic":
                if(StringUtils.isNotBlank(user.getAccount())
                        || StringUtils.isNotBlank(user.getPassword())
                        || user.getLevel() !=null
                        || user.getStatus() != null
                        || user.getPoints() != null
                        || user.getPointsSum() !=null)
                return false;
                break;
            case "account":
                if(StringUtils.isNotBlank(user.getAccount())
                        || StringUtils.isNotBlank(user.getName())
                        || StringUtils.isNotBlank(user.getEmail())
                        || StringUtils.isNotBlank(user.getMobile())
                        || StringUtils.isNotBlank(user.getNickname())
                        || user.getLevel() !=null
                        || user.getPoints() != null
                        || user.getPointsSum() !=null)
                    return false;
                break;
        }
        return true;
    }

    @Permission("4104")
    @ResponseBody
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public JSONObject enableUser(@RequestParam(value = "id")Integer id){
        try{
            User user = new User();
            user.setId(id);
            user.setStatus(1);
            return userService.edit(user);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("4104")
    @ResponseBody
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public JSONObject disableUser(@RequestParam(value = "id")Integer id){
        try{
            User user = new User();
            user.setId(id);
            user.setStatus(0);
            return userService.edit(user);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("4000")
    @ResponseBody
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
    @Permission("4200")
    @RequestMapping("/check")
    public String getUserPoints(ModelMap model){
        model.addAttribute("pageTitle","会员等级积分查询-"+userMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "user");
        model.addAttribute("LeftMenuFlag", "check");
        return adminHtml +"user_level_check";
    }


    @Permission("4300")
    @RequestMapping("/modify")
    public String getUserLevel(ModelMap model){
        model.addAttribute("pageTitle","会员等级积分修改-"+userMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "user");
        model.addAttribute("LeftMenuFlag", "modify");
        return adminHtml +"user_level_modify";
    }

    @Permission("4301")
    @ResponseBody
    @RequestMapping(value = "/points/submit", method = RequestMethod.POST)
    public JSONObject editUserPoints(@RequestParam(value = "account")String account,
                                     @RequestParam(value = "points")Integer points,
                                     @RequestParam(value = "type")Integer type) {
        try {
            User user = userService.get(account);
            //参数检查
            if(points<1){
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
                //累积积分
                points = oldPoints+points;
            }else {
                return CommonOperation.fail();
            }

            User newUser = new User();
            newUser.setId(user.getId());
            newUser.setPoints(points);
            return userService.edit(newUser);
        }catch (JsonException e){
            return e.toJson();
        }
    }


    @Permission("4302")
    @ResponseBody
    @RequestMapping("/level/submit")
    public JSONObject editUserLevel(@RequestParam(value = "account")String account,
                                    @RequestParam(value = "level")Integer level,
                                    @RequestParam(value = "type")Integer type) {
        try {
            User user = userService.get(account);
            //参数检查
            if(level<1){
                return CommonOperation.fail("等级必须为正数");
            }
            int oldLevel = user.getLevel();
            if(type == 0){
                //降低等级
                level = oldLevel-level;
                if(level<0){
                    return CommonOperation.fail("等级已为0");
                }
            }else if(type == 1){
                //提升等级
                level = oldLevel+level;
            }else {
                return CommonOperation.fail();
            }

            User newUser = new User();
            newUser.setId(user.getId());
            newUser.setLevel(level);
            return userService.edit(newUser);
        }catch (JsonException e){
            return e.toJson();
        }
    }

}
