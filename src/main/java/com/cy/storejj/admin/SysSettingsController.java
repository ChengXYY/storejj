package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Membership;
import com.cy.storejj.model.SysDict;
import com.cy.storejj.service.SysDictService;
import com.cy.storejj.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SysSettingsController extends AdminConfig {

    @Autowired
    private MembershipService membershipService;
    @Autowired
    private SysDictService dictService;

    @Permission("6500")
    @RequestMapping("/membership/list")
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
    @RequestMapping("/membership/add")
    public String add(ModelMap model){
        return adminHtml+"membership_add";
    }


    @Permission("6502")
    @ResponseBody
    @RequestMapping("/membership/add/submit")
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
    @Permission("6505")
    @ResponseBody
    @RequestMapping("/user/refresh")
    public JSONObject refresh(){
        return null;
    }

    @Permission("6600")
    @RequestMapping("/store/list")
    public String storeList(ModelMap model){

        List<SysDict> list = dictService.getList("StoreSettings");

        model.addAttribute("list", list);
        model.addAttribute("totalCount", list.size());

        model.addAttribute("pageTitle",listPageTitle+membershipModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "system");
        model.addAttribute("LeftMenuFlag", "membership");
        return adminHtml +"store_list";
    }

    @Permission("6602")
    @RequestMapping("/store/add")
    public String storeAdd(ModelMap model){

        return adminHtml +"store_add";
    }

    @Permission("6602")
    @RequestMapping("/store/add/submit")
    @ResponseBody
    public JSONObject storeAdd(SysDict dict){
        try {
            dict.setType("StoreSettings");
            return dictService.add(dict);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6603")
    @RequestMapping(value = "/store/edit", method = RequestMethod.GET)
    public String storeEdit(@RequestParam("id")Integer id, ModelMap model){
        try {
            SysDict dict = dictService.get(id);
            model.addAttribute("sysDict", dict);
            return adminHtml +"store_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("6603")
    @RequestMapping("/store/edit/submit")
    @ResponseBody
    public JSONObject storeEdit(SysDict dict){
        try {
            return dictService.edit(dict);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6604")
    @RequestMapping(value = "/store/remove", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject storeEdit(@RequestParam("id")Integer id){
        try {
            return dictService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
