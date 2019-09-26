package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Membership;
import com.cy.storejj.model.SysDict;
import com.cy.storejj.service.SysDictService;
import com.cy.storejj.service.MembershipService;
import com.cy.storejj.service.UserService;
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
    @Autowired
    private UserService userService;


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
    @RequestMapping(value = "/membership/edit", method = RequestMethod.GET)
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
    @RequestMapping(value = "/membership/edit/submit")
    public JSONObject edit(Membership membership){
        try {
            return membershipService.edit(membership);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6504")
    @ResponseBody
    @RequestMapping(value = "/membership/remove", method = RequestMethod.POST)
    public JSONObject remove(@RequestParam(value = "id")Integer id){
        try {
            return membershipService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6505")
    @ResponseBody
    @RequestMapping(value = "/user/refresh", method = RequestMethod.POST)
    public JSONObject refresh(){
        try{
            return userService.levelRefresh();
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6600")
    @RequestMapping("/store/list")
    public String storeList(ModelMap model){

        List<SysDict> list = dictService.getList("StoreSettings");

        model.addAttribute("list", list);
        model.addAttribute("totalCount", list.size());

        model.addAttribute("pageTitle",listPageTitle+storeModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "system");
        model.addAttribute("LeftMenuFlag", "store");
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

    //notice
    @Permission("6700")
    @RequestMapping("/notice/list")
    public String noticeList(ModelMap model){

        List<SysDict> list = dictService.getList("NoticeSettings");

        model.addAttribute("list", list);
        model.addAttribute("totalCount", list.size());

        model.addAttribute("pageTitle",listPageTitle+noticeModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "system");
        model.addAttribute("LeftMenuFlag", "notice");
        return adminHtml +"notice_list";
    }

    @Permission("6702")
    @RequestMapping("/notice/add")
    public String noticeAdd(ModelMap model){

        return adminHtml +"notice_add";
    }

    @Permission("6702")
    @RequestMapping("/notice/add/submit")
    @ResponseBody
    public JSONObject noticeAdd(SysDict dict){
        try {
            dict.setType("NoticeSettings");
            return dictService.add(dict);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6703")
    @RequestMapping(value = "/notice/edit", method = RequestMethod.GET)
    public String noticeEdit(@RequestParam("id")Integer id, ModelMap model){
        try {
            SysDict dict = dictService.get(id);
            model.addAttribute("sysDict", dict);
            return adminHtml +"notice_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("6703")
    @RequestMapping("/notice/edit/submit")
    @ResponseBody
    public JSONObject noticeEdit(SysDict dict){
        try {
            return dictService.edit(dict);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6704")
    @RequestMapping(value = "/notice/remove", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject noticeEdit(@RequestParam("id")Integer id){
        try {
            return dictService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    //service
    @Permission("6800")
    @RequestMapping("/service/list")
    public String serviceList(ModelMap model){

        List<SysDict> list = dictService.getList("ServiceSettings");

        model.addAttribute("list", list);
        model.addAttribute("totalCount", list.size());

        model.addAttribute("pageTitle",listPageTitle+serviceModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "system");
        model.addAttribute("LeftMenuFlag", "service");
        return adminHtml +"service_list";
    }

    @Permission("6802")
    @RequestMapping("/service/add")
    public String serviceAdd(ModelMap model){

        return adminHtml +"service_add";
    }

    @Permission("6802")
    @RequestMapping("/service/add/submit")
    @ResponseBody
    public JSONObject serviceAdd(SysDict dict){
        try {
            dict.setType("ServiceSettings");
            return dictService.add(dict);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6803")
    @RequestMapping(value = "/service/edit", method = RequestMethod.GET)
    public String serviceEdit(@RequestParam("id")Integer id, ModelMap model){
        try {
            SysDict dict = dictService.get(id);
            model.addAttribute("sysDict", dict);
            return adminHtml +"service_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("6803")
    @RequestMapping("/service/edit/submit")
    @ResponseBody
    public JSONObject serviceEdit(SysDict dict){
        try {
            return dictService.edit(dict);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6804")
    @RequestMapping(value = "/service/remove", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject serviceEdit(@RequestParam("id")Integer id){
        try {
            return dictService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6900")
    @RequestMapping(value = "/contact/settings")
    public String contactSettings(ModelMap modelMap){
        List<SysDict> list = dictService.getList("ContactSettings");
        SysDict sysDict = new SysDict();
        if(list.size()>0){
            sysDict = list.get(0);
        }
        modelMap.addAttribute("sysDict", sysDict);
        modelMap.addAttribute("pageTitle",listPageTitle+serviceModuleTitle+systemTitle);
        modelMap.addAttribute("TopMenuFlag", "system");
        modelMap.addAttribute("LeftMenuFlag", "contact");
        return adminHtml+"contact_settings";
    }

    @Permission("6901")
    @RequestMapping(value = "/contact/settings/submit", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject contactSettings(SysDict dict){

        List<SysDict> list = dictService.getList("ContactSettings");
        try {
            if(list.size() <1 && dict.getId() == null){
                dict.setType("ContactSettings");
                return dictService.add(dict);
            }
            if(list.size()>0){
                dict.setId(list.get(0).getId());
            }
            return dictService.edit(dict);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
