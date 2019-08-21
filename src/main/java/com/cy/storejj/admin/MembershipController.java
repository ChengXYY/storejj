package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Membership;
import com.cy.storejj.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Permission("6500")
@Controller
@RequestMapping("/membership")
public class MembershipController extends AdminConfig {

    @Autowired
    private MembershipService membershipService;

    @RequestMapping("/list")
    public String list(ModelMap model){

        List<Membership> list = membershipService.getListAll();

        model.addAttribute("list", list);
        model.addAttribute("totalCount", list.size());

        model.addAttribute("pageTitle",listPageTitle+membershipModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "system");
        model.addAttribute("LeftMenuFlag", "membership");
        return adminHtml +"membership_list";
    }

    @Permission("6502")
    @RequestMapping("/add")
    public String add(ModelMap model){
        return adminHtml+"membership_add";
    }


    @Permission("6502")
    @ResponseBody
    @RequestMapping("/add/submit")
    public JSONObject add(Membership membership){
        try {
            return membershipService.add(membership);
        }catch (JsonException e){
            return e.toJson();
        }
    }


    @Permission("6503")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true)Integer id, ModelMap model){
        try {
            Membership item = membershipService.get(id);
            model.addAttribute("item", item);
            return adminHtml+"membership_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("6503")
    @ResponseBody
    @RequestMapping(value = "/edit/submit")
    public JSONObject edit(Membership membership){
        try {
            return membershipService.edit(membership);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6504")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove(@RequestParam(value = "id")Integer id){
        try {
            return membershipService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @ResponseBody
    @RequestMapping("/user/refresh")
    public JSONObject refresh(){
        return null;
    }
}
