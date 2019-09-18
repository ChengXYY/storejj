package com.cy.storejj.web;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.WebConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.*;
import com.cy.storejj.service.*;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController extends WebConfig {

    @Autowired
    private SitePageService sitepageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private MembershipService membershipService;

    @Autowired
    private SuggestionService suggestionService;

    // 首页
    @RequestMapping(value = {"/", "", "/index", "/index/"})
    public String index(ModelMap modelMap){
        //店铺
        List<SysDict> storeList = sysDictService.getList("StoreSettings");
        //服务
        List<SysDict> serviceList = sysDictService.getList("ServiceSettings");
        //联系方式
        List<SysDict> contactList = sysDictService.getList("ContactSettings");


        Map<String, Object> filter = new HashMap<>();

        // userCount
        int userCount = userService.getCount(filter);

        //levelCount
        int levelCount = membershipService.getListAll().size();

        //产品种类
        filter.put("orderBy", "sort desc");
        List<Category> categoryList = categoryService.getList(filter);
        // categoryCount
        int categoryCount = categoryList.size();

        //文章
        filter.put("orderby", "create_time desc");
        filter.put("page", 0);
        filter.put("pageSize", 4);
        List<Article> articleList = articleService.getList(filter);

        //产品
        filter.put("pageSize", 16);
        filter.put("isDelete", 0);
        List<Product> productList = productService.getList(filter);

        // productCount
        int productCount = productService.getCount(filter);

        modelMap.addAttribute("category", categoryList);
        modelMap.addAttribute("product", productList);
        modelMap.addAttribute("article", articleList);
        modelMap.addAttribute("store", storeList);
        modelMap.addAttribute("service", serviceList);
        modelMap.addAttribute("contact", contactList.get(0));

        modelMap.addAttribute("userCount", userCount);
        modelMap.addAttribute("levelCount", levelCount);
        modelMap.addAttribute("categoryCount", categoryCount);
        modelMap.addAttribute("productCount", productCount);

        modelMap.addAttribute("pageTitle", systemTitle);
        modelMap.addAttribute("topFlag", "index");
        return webHtml+"index";
    }

    // 新闻页
    @RequestMapping("/story")
    public String stories(@RequestParam Map<String, Object> param,
                          HttpServletRequest request,
                          ModelMap model){
        model.addAttribute("pageTitle", "新闻·故事 - "+systemTitle);
        model.addAttribute("topFlag", "blog");

        String currentUrl = request.getRequestURI();
        if(param.get("name")!=null && StringUtils.isNotBlank(param.get("name").toString())){
            currentUrl = setUrlParam(currentUrl, "name", param.get("name").toString());
        }else {
            param.remove("name");
        }
        param.put("currentUrl", currentUrl);
        int totalCount = articleService.getCount(param);
        param.put("totalCount", totalCount);
        param.put("pageSize", 5);
        setPagenation(param);

        List<Article> list = articleService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("articleList", list);
        return webHtml +"story";
    }

    //新闻详情
    @RequestMapping("/story/detail")
    public String story(ModelMap modelMap){

        modelMap.addAttribute("pageTitle", "新闻·故事 - "+systemTitle);
        modelMap.addAttribute("topFlag", "blog");
        return webHtml+"story_detail";
    }

    //商品页
    @RequestMapping(value = "/jewellery", method = RequestMethod.GET)
    public String product(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();

        if(param.get("code")!=null && StringUtils.isNotBlank(param.get("code").toString())){
            currentUrl = setUrlParam(currentUrl, "code", param.get("code").toString());
        }else {
            param.remove("code");
        }

        if(param.get("name")!=null && StringUtils.isNotBlank(param.get("name").toString())){
            currentUrl = setUrlParam(currentUrl, "name", param.get("name").toString());
        }else {
            param.remove("name");
        }

        if(param.get("categoryCode")!=null && StringUtils.isNotBlank(param.get("categoryCode").toString())){
            currentUrl = setUrlParam(currentUrl, "categoryCode", param.get("categoryCode").toString());
        }else {
            param.remove("categoryCode");
        }

        param.put("currentUrl", currentUrl);
        int totalCount = productService.getCount(param);
        param.put("totalCount", totalCount);
        param.put("pageSize", 5);
        setPagenation(param);

        List<Product> list = productService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("productList", list);

        List<Category> categoryList = categoryService.getList(null);
        model.addAttribute("categoryList", categoryList);

        //通知
        List<SysDict> noticeList = sysDictService.getList("NoticeSettings");
        model.addAttribute("noticeList", noticeList);

        model.addAttribute("pageTitle", "珠宝·翡翠 - "+systemTitle);
        model.addAttribute("topFlag", "product");
        return webHtml +"jewellery";
    }

    @ResponseBody
    @RequestMapping(value = "/suggestion/submit", method = RequestMethod.POST)
    public JSONObject suggest(Suggestion suggestion){
        try{
            return suggestionService.add(suggestion);
        }catch (JsonException e){
            return e.toJson();
        }
    }

}
