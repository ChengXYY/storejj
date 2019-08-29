package com.cy.storejj.web;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.WebConfig;
import com.cy.storejj.model.Article;
import com.cy.storejj.model.Category;
import com.cy.storejj.model.Product;
import com.cy.storejj.model.SysDict;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController extends WebConfig {

    private JSONObject pageData = new JSONObject();

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

    // 首页
    @RequestMapping(value = {"/", "", "/index", "/index/"})
    public String index(ModelMap modelMap){
        //店铺
        List<SysDict> storeList = sysDictService.getList("StoreSettings");

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

        pageData.put("category", categoryList);
        pageData.put("product", productList);
        pageData.put("article", articleList);
        pageData.put("store", storeList);

        pageData.put("userCount", userCount);
        pageData.put("levelCount", levelCount);
        pageData.put("categoryCount", categoryCount);
        pageData.put("productCount", productCount);

        pageData.put("title", systemTitle);
        pageData.put("topflag", "index");
        modelMap.addAttribute("page", pageData);
        return webHtml+"index";
    }

    // 新闻页
    @RequestMapping("/story")
    public String stories(ModelMap modelMap){
        pageData.put("title", "新闻·故事 - "+systemTitle);
        pageData.put("topflag", "blog");
        modelMap.addAttribute("page", pageData);
        return webHtml +"story";
    }

    //新闻详情
    @RequestMapping("/story/detail")
    public String story(ModelMap modelMap){

        pageData.put("title", "新闻·故事 - "+systemTitle);
        pageData.put("topflag", "blog");
        modelMap.addAttribute("page", pageData);
        return webHtml+"story_detail";
    }

    //商品页
    @RequestMapping(value = "/jewellery", method = RequestMethod.GET)
    public String product(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();

        if(param.get("code")!=null && StringUtils.isNotBlank(param.get("code").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "code", param.get("code").toString());
        }else {
            param.remove("code");
        }

        if(param.get("name")!=null && StringUtils.isNotBlank(param.get("name").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "name", param.get("name").toString());
        }else {
            param.remove("name");
        }

        if(param.get("categoryCode")!=null && StringUtils.isNotBlank(param.get("categoryCode").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "categoryCode", param.get("categoryCode").toString());
        }else {
            param.remove("categoryCode");
        }

        param.put("currentUrl", currentUrl);
        param.put("isShop", 0); //商城产品不展示
        param.put("isDelete",0);
        int totalCount = productService.getCount(param);
        param.put("totalCount", totalCount);
        param.put("pageSize", 5);
        setPagenation(param);

        List<Product> list = productService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("productList", list);

        List<Category> categoryList = categoryService.getList(null);
        model.addAttribute("categoryList", categoryList);

        pageData.put("title", "珠宝·翡翠 - "+systemTitle);
        pageData.put("topflag", "product");
        model.addAttribute("page", pageData);
        return webHtml +"jewellery";
    }

    @RequestMapping(value = "/shopall", method = RequestMethod.GET)
    public String shop(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model) {
        String currentUrl = request.getRequestURI();

        if (param.get("code") != null && StringUtils.isNotBlank(param.get("code").toString())) {
            currentUrl = CommonOperation.setUrlParam(currentUrl, "code", param.get("code").toString());
        } else {
            param.remove("code");
        }

        if (param.get("name") != null && StringUtils.isNotBlank(param.get("name").toString())) {
            currentUrl = CommonOperation.setUrlParam(currentUrl, "name", param.get("name").toString());
        } else {
            param.remove("name");
        }

        param.put("currentUrl", currentUrl);
        param.put("isShop", 1); //商城产品
        param.put("isDelete", 0);
        int totalCount = productService.getCount(param);
        param.put("totalCount", totalCount);
        param.put("pageSize", 5);
        setPagenation(param);

        List<Product> list = productService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("shopList", list);

        pageData.put("title", "积分商城 - " + systemTitle);
        pageData.put("topflag", "product");
        model.addAttribute("page", pageData);
        return webHtml + "jewellery";
    }

}
