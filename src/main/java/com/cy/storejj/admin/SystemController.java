package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Admin;
import com.cy.storejj.model.AdminGroup;
import com.cy.storejj.model.AdminLog;
import com.cy.storejj.service.AdminService;
import com.cy.storejj.service.AdminGroupService;
import com.cy.storejj.service.AdminLogService;
import com.cy.storejj.service.AuthService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController extends AdminConfig {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminGroupService admingroupService;
    @Autowired
    private AdminLogService adminlogService;
    @Autowired
    private AuthService authService;


    @Permission("6100")
    @RequestMapping("/admin/list")
    public String adminList(HttpSession session, ModelMap model){
        Map<String, Object>filter = new HashMap<String, Object>();
        //只获取当前用户下及用户
        filter.put("parentid", session.getAttribute(adminId));
        filter.put("orderby", "id asc");
        List<Admin> list = adminService.getList(filter);

        int totalCount = list.size();
        model.addAttribute("list", list);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageTitle",listPageTitle+adminModuleTitle+systemTitle);

        model.addAttribute("TopMenuFlag", "system");
        model.addAttribute("LeftMenuFlag", "admin");
        return adminHtml +"admin_list";
    }

    @Permission("6102")
    @RequestMapping("/admin/add")
    public String adminAdd(HttpSession session, ModelMap model){
        List<AdminGroup> list = admingroupService.getListAll(Integer.parseInt(session.getAttribute(adminId).toString()));
        model.addAttribute("list", list);
        model.addAttribute("pageTitle",addPageTitle+adminModuleTitle+systemTitle);
        return adminHtml +"admin_add";
    }

    @Permission("6102")
    @ResponseBody
    @RequestMapping(value = "/admin/add/submit", produces = {"application/json;charset=UTF-8"})
    public JSONObject adminAdd(Admin admin, HttpSession session){
        if(admin.getName()==null || admin.getName().isEmpty()){
            admin.setName(admin.getAccount());
        }
        admin.setParentId(Integer.parseInt(session.getAttribute(adminId).toString()));

        try {
            return adminService.add(admin);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6103")
    @RequestMapping("/admin/edit/{id}")
    public String adminEdit(@PathVariable("id") Integer id, HttpSession session, ModelMap model){
        try {
            Admin admin = adminService.get(id);

            List<AdminGroup> list = admingroupService.getListAll(Integer.parseInt(session.getAttribute(adminId).toString()));
            model.addAttribute("list", list);
            model.addAttribute("admin", admin);
            model.addAttribute("pageTitle",editPageTitle+adminModuleTitle+systemTitle);
            return adminHtml +"admin_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }


    }

    @Permission("6103")
    @ResponseBody
    @RequestMapping(value = "/admin/edit/submit", method = RequestMethod.POST)
    public JSONObject editAdmin(Admin admin ){

        try {
            return adminService.edit(admin);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6104")
    @ResponseBody
    @RequestMapping(value = "/admin/remove", method = RequestMethod.POST)
    public JSONObject removeAdmin(@RequestParam(value = "id", required = true)Integer id){

        try {
            return adminService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6105")
    @ResponseBody
    @RequestMapping(value = "/admin/resetpwd/submit", produces = {"application/json;charset=UTF-8"})
    public JSONObject passwordReset(Integer id){

        try{
            return adminService.resetPassword(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    // AdminGroup 处理
    @Permission("6200")
    @RequestMapping("/admingroup/list")
    public String admingroupList(HttpSession session, ModelMap model){
        List<AdminGroup> list = admingroupService.getListAll(Integer.parseInt(session.getAttribute(adminId).toString()));
        model.addAttribute("list", list);

        int totalCount = list.size();
        model.addAttribute("pageTitle",listPageTitle+admingroupModuleTitle+systemTitle);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("TopMenuFlag", "system");
        model.addAttribute("LeftMenuFlag", "admingroup");
        return adminHtml +"admingroup_list";
    }

    @Permission("6202")
    @RequestMapping(value = "/admingroup/add", method = RequestMethod.GET)
    public String admingroupAdd(ModelMap model){
    //    model.addAttribute("parentId", parentId);
        return adminHtml +"admingroup_add";
    }

    @Permission("6202")
    @ResponseBody
    @RequestMapping("/admingroup/add/submit")
    public JSONObject admingroupAdd(AdminGroup admingroup, HttpSession session){
        admingroup.setParentId(Integer.parseInt(session.getAttribute(adminId).toString()));
        try {
            return admingroupService.add(admingroup);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6203")
    @RequestMapping(value = "/admingroup/edit", method = RequestMethod.GET)
    public String admingroupEdit(@RequestParam("id") Integer id, ModelMap model){
        try {
            AdminGroup admingroup = admingroupService.get(id);
            model.addAttribute("item", admingroup);
            return adminHtml +"admingroup_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }

    }

    @Permission("6203")
    @ResponseBody
    @RequestMapping("/admingroup/edit/submit")
    public JSONObject admingroupEdit(AdminGroup admingroup){

        try {
            return admingroupService.edit(admingroup);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6206")
    @ResponseBody
    @RequestMapping(value = "/admingroup/remove", method = RequestMethod.POST)
    public JSONObject admingroupRemove(@RequestParam(value = "id") Integer id){
        try {
            return admingroupService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("6204")
    @RequestMapping(value = "/admingroup/members", method = RequestMethod.GET)
    public String admingroupMembers(@RequestParam(value = "id") Integer id, ModelMap model){
        try {
            Map<String, Object> filter = new HashMap<>();
            filter.put("groupId", id);
            List<Admin> members = adminService.getList(filter);
            model.addAttribute("list", members);
            model.addAttribute("pageTitle",listPageTitle+admingroupModuleTitle+systemTitle);

            model.addAttribute("TopMenuFlag", "system");
            model.addAttribute("LeftMenuFlag", "admingroup");
            return adminHtml +"admingroup_members";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("6205")
    @ResponseBody
    @RequestMapping(value = "/admingroup/members/remove", method = RequestMethod.POST)
    public JSONObject admingroupMembers(@RequestParam(value = "ids") String ids, ModelMap model){
        try {
            return adminService.resetGroups(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    //权限
    @Permission("6300")
    @RequestMapping("/admingroup/auth")
    public String admingrouAuth(HttpSession session,
                                    ModelMap model){
        try {
            //group list
            List<AdminGroup> groupList = admingroupService.getListAll(0);
            model.addAttribute("groupList", groupList);
            model.addAttribute("pageTitle",authPageTitle+admingroupModuleTitle+systemTitle);

            model.addAttribute("TopMenuFlag", "system");
            model.addAttribute("LeftMenuFlag", "adminauth");
            return adminHtml +"admingroup_auth";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("6301")
    @ResponseBody
    @RequestMapping(value = "/admingroup/auth/list", method = RequestMethod.POST)
    public JSONObject getMyAuthList(@RequestParam(value = "groupid")Integer groupId){
        try {
            AdminGroup group = admingroupService.get(groupId);
            String[] auth = {};
            if(StringUtils.isNotBlank(group.getAuth())){
                auth = group.getAuth().split(",");
            }
            JSONObject res = new JSONObject();
            res.put("list", auth);

            return res;
        }catch (JsonException e){
            return e.toJson();
        }

    }


    @Permission("6301")
    @ResponseBody
    @RequestMapping(value = "/admingroup/auth/tree")
    public JSONObject getTree(){
        List<Map<String, Object>> treeList = authService.getAuthTree();
        JSONObject res = new JSONObject();
        res.put("tree", treeList);

        return res;
    }


    @Permission("6301")
    @ResponseBody
    @RequestMapping(value = "/admingroup/auth/save", method = RequestMethod.POST)
    public JSONObject authSave(Integer id, @RequestParam(value = "authcodes[]") String[] authcodes){
        try {
            return admingroupService.changeAuth(id, authcodes);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    //log list
    @Permission("6400")
    @RequestMapping(value = "/adminlog/list", method = RequestMethod.GET)
    public String adminlogList(@RequestParam Map<String, Object> param,
                               HttpServletRequest request,
                               ModelMap model){

        String currentUrl = request.getRequestURI();

        if(param.get("content")!=null && StringUtils.isNotBlank(param.get("content").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "content", param.get("content").toString());
        }
        param.put("currentUrl", currentUrl);
        int totalCount = adminlogService.getCount(param);
        param.put("totalCount", totalCount);
        param = setPagenation(param);

        List<AdminLog> list = adminlogService.getList(param);

        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+adminlogModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "system");
        model.addAttribute("LeftMenuFlag", "adminlog");
        return adminHtml +"adminlog_list";
    }
}
