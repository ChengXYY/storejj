package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.PageTpl;
import com.cy.storejj.service.PageTplService;
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
@RequestMapping("/pagetpl")
@Permission("1200")
public class PageTplController extends AdminConfig {
    @Autowired
    private PageTplService pagetplService;

    @RequestMapping(value = {"", "/", "index", "list"}, method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object>param ,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();
        currentUrl = handleParam(param, currentUrl);
        param.put("currentUrl", currentUrl);

        int totalCount = pagetplService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<PageTpl> list = pagetplService.getList(param);

        model.addAllAttributes(param);

        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+pagetplModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "sitepage");
        model.addAttribute("LeftMenuFlag", "tpl");
        return adminHtml +"tpl_list";
    }

    @Permission("1202")
    @RequestMapping("/add")
    public String add(ModelMap model){
        model.addAttribute("pageTitle",addPageTitle+pagetplModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "sitepage");
        model.addAttribute("LeftMenuFlag", "tpl");
        return adminHtml +"tpl_add";
    }

    @Permission("1202")
    @ResponseBody
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    public JSONObject add(PageTpl pagetpl, HttpSession session){
        pagetpl.setCreateBy(session.getAttribute(adminAccount).toString());
        try {
            return pagetplService.add(pagetpl);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("1203")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true)Integer id, ModelMap model){
        try {
            JSONObject tpl = pagetplService.get(id);
            model.addAttribute("tpl", tpl);
            model.addAttribute("pageTitle",addPageTitle+pagetplModuleTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "sitepage");
            model.addAttribute("LeftMenuFlag", "tpl");
            return adminHtml +"tpl_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }

    }

    @Permission("1203")
    @ResponseBody
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    public JSONObject edit(PageTpl pagetpl, HttpSession session){
        pagetpl.setCreateBy(session.getAttribute(adminAccount).toString());
        try {
            return pagetplService.edit(pagetpl);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public JSONObject get(@RequestParam(value = "id", required = true) Integer id){
        try {
            return pagetplService.get(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("1205")
    @ResponseBody
    @RequestMapping("/batchremove")
    public JSONObject batchRemove(@RequestParam(value = "ids")String ids){
        try {
            return pagetplService.remove(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
