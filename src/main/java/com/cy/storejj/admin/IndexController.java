package com.cy.storejj.admin;

import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController extends AdminConfig {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminGroupService admingroupService;

    @Autowired
    private AdminLogService adminlogService;

    @Autowired
    private SitePageService sitepageService;

    @Autowired
    private PageTplService pagetplService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;



    @RequestMapping(value = {adminHtml +"index", adminHtml +"", "/admin"})
    public String index(ModelMap model){
        model.addAttribute("TopMenuFlag", "index");
        model.addAttribute("pageTitle",indexModuleTitle+systemTitle);
        return adminHtml +"index";
    }

    @Permission("9001")
    @RequestMapping(value = {"/system", "/system/index", "/system/"})
    public String system(ModelMap modelMap){
        Map<String, Object> filter = new HashMap<>();
        int adminCount = adminService.getCount(filter);
        int groupCount = admingroupService.countAll(0);

        filter.put("content", "管理员登录");
        int loginCount = adminlogService.getCount(filter);
        modelMap.addAttribute("adminCount", adminCount);
        modelMap.addAttribute("groupCount", groupCount);
        modelMap.addAttribute("loginCount", loginCount);
        modelMap.addAttribute("TopMenuFlag", "system");
        modelMap.addAttribute("pageTitle",systemMenuTitle+systemTitle);
        return adminHtml +"system_index";
    }

    @Permission("1001")
    @RequestMapping(value = {"/site", "/site/index", "/site/"})
    public String sitepage(ModelMap modelMap){
        Map<String, Object> filter = new HashMap<>();
        int tplCount = pagetplService.getCount(filter);
        int siteCount = sitepageService.getCount(filter);
        modelMap.addAttribute("tplCount", tplCount);
        modelMap.addAttribute("siteCount", siteCount);
        modelMap.addAttribute("TopMenuFlag", "sitepage");
        modelMap.addAttribute("pageTitle",siteMenuTitle+systemTitle);
        return adminHtml +"sitepage_index";
    }

    @Permission("1002")
    @RequestMapping(value = {"/resource", "/resource/index", "/resource/"})
    public String resource(ModelMap modelMap){
        Map<String, Object> filter = new HashMap<>();
        int pictureCount = pictureService.getCount(filter);
        int articleCount = articleService.getCount(filter);
        modelMap.addAttribute("pictureCount", pictureCount);
        modelMap.addAttribute("articleCount", articleCount);
        modelMap.addAttribute("TopMenuFlag", "resource");
        modelMap.addAttribute("pageTitle",resourceMenuTitle+systemTitle);
        return adminHtml +"resource_index";
    }

    @Permission("1003")
    @RequestMapping(value = {"/products", "/products/index", "/products/"})
    public String product(ModelMap modelMap){
        Map<String, Object> filter = new HashMap<>();
        int categoryCount = categoryService.getCount(filter);
        modelMap.addAttribute("categoryCount", categoryCount);
        modelMap.addAttribute("TopMenuFlag", "product");
        modelMap.addAttribute("pageTitle",productMenuTitle+systemTitle);
        return adminHtml +"product_index";
    }

    @Permission("1004")
    @RequestMapping(value = {"/user", "/user/index", "/user/"})
    public String membership(ModelMap modelMap){
        Map<String, Object> filter = new HashMap<>();
        int userCount = userService.getCount(filter);
        modelMap.addAttribute("userCount", userCount);
        modelMap.addAttribute("TopMenuFlag", "user");
        modelMap.addAttribute("pageTitle",userMenuTitle+systemTitle);
        return adminHtml +"user_index";
    }
}
