package com.cy.storejj.web;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.WebConfig;
import com.cy.storejj.model.Category;
import com.cy.storejj.model.Product;
import com.cy.storejj.service.CategoryService;
import com.cy.storejj.service.ProductService;
import com.cy.storejj.service.SitePageService;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController extends WebConfig {

    private JSONObject pageData = new JSONObject();

    //自定义页面

    @Autowired
    private SitePageService sitepageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"/", "", "/index", "/index/"})
    public String index(ModelMap modelMap){
        Map<String, Object> filter = new HashMap<>();
        List<Category> categoryList = categoryService.getList(filter);
        filter.put("page", 0);
        filter.put("pageSize", 9);
        List<Product> productList = productService.getList(filter);
        pageData.put("category", categoryList);
        pageData.put("product", productList);
        pageData.put("title", systemTitle);
        pageData.put("topflag", "index");
        modelMap.addAttribute("page", pageData);
        return webHtml+"index";
    }

    @RequestMapping("/product")
    public String product(@RequestParam(value = "page", defaultValue = "1", required = false)Integer page,
                          HttpServletRequest req,
                          ModelMap modelMap){
        String requestUrl =  req.getRequestURI();

        pageData.put("title", "珠宝·翡翠 - "+systemTitle);
        pageData.put("topflag", "product");
        //   pageData.put("list", list);
        pageData.put("currentPage", page);
        pageData.put("pageCount", 15);
        pageData.put("totalCount", 143);
        pageData.put("currentUrl", requestUrl);
  modelMap.addAttribute("page", pageData);

        return webHtml+"product";
    }

    @RequestMapping("/stories")
    public String stories(ModelMap modelMap){
        pageData.put("title", "新闻·故事 - "+systemTitle);
        pageData.put("topflag", "blog");
        modelMap.addAttribute("page", pageData);
        return webHtml+"story";
    }

    @RequestMapping("/story")
    public String story(ModelMap modelMap){

        pageData.put("title", "新闻·故事 - "+systemTitle);
        pageData.put("topflag", "blog");
        modelMap.addAttribute("page", pageData);
        return webHtml+"story_detail";
    }
}
