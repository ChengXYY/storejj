package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.PageTpl;
import com.cy.storejj.model.SitePage;
import com.cy.storejj.service.PageTplService;
import com.cy.storejj.service.SitePageService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sitepage")
@Permission("1100")
public class SitePageController extends AdminConfig {

    @Autowired
    private SitePageService sitepageService;

    @Autowired
    private PageTplService pagetplService;

    @RequestMapping(value = {"", "/index", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){

        String currentUrl = request.getRequestURI();
        currentUrl = handleParam(param, currentUrl);
        param.put("currentUrl", currentUrl);

        int totalCount = sitepageService.getCount(param);
        param.put("totalCount", totalCount);
        param = setPagenation(param);

        List<SitePage> list = sitepageService.getList(param);

        model.addAllAttributes(param);

        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+sitepageModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "sitepage");
        model.addAttribute("LeftMenuFlag", "page");
        return adminHtml +"site_list";
    }

    @Permission("1102")
    @RequestMapping("/add")
    public String add(ModelMap model){
        //获取模板列表
        List<PageTpl> list = pagetplService.getSelectList();
        model.addAttribute("list", list);
        model.addAttribute("pageTitle",addPageTitle+sitepageModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "sitepage");
        model.addAttribute("LeftMenuFlag", "page");
        return adminHtml +"site_add";
    }

    @Permission("1102")
    @ResponseBody
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    public JSONObject add(SitePage sitepage, HttpSession session){
        sitepage.setCreateBy(session.getAttribute(adminAccount).toString());
        try {
            return sitepageService.add(sitepage);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("1103")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true)Integer id, ModelMap model){

        try {
            List<PageTpl> list = pagetplService.getSelectList();
            model.addAttribute("list", list);

            SitePage sitepage = sitepageService.get(id);
            model.addAttribute("page", sitepage);
            model.addAttribute("pageTitle",editPageTitle+sitepageModuleTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "sitepage");
            model.addAttribute("LeftMenuFlag", "page");

            return adminHtml +"site_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("1103")
    @ResponseBody
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    public JSONObject edit(SitePage sitepage){
        try {
            return sitepageService.edit(sitepage);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public String preview(@RequestParam(value = "id", required = true)String id, ModelMap model){
        if(id==null || id.isEmpty() || id.equals("0")){
            return "/error/404";
        }
        Integer pageId = Integer.parseInt(id);
        try {
            SitePage page = sitepageService.get(pageId);
            model.addAttribute("page", page);
            return adminHtml +"site_preview";

        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("1104")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove(@RequestParam(value = "id", required = true)Integer id){

        try {
            return sitepageService.remove(id);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("1105")
    @ResponseBody
    @RequestMapping("/batchremove")
    public JSONObject batchRemove(@RequestParam(value = "ids")String ids){
        try {
            return sitepageService.remove(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
