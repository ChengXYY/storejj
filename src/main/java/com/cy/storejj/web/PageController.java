package com.cy.storejj.web;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.WebConfig;
import com.cy.storejj.model.Category;
import com.cy.storejj.model.Product;
import com.cy.storejj.model.SysDict;
import com.cy.storejj.service.CategoryService;
import com.cy.storejj.service.ProductService;
import com.cy.storejj.service.SitePageService;
import com.cy.storejj.service.SysDictService;
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

    //自定义页面

    @Autowired
    private SitePageService sitepageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SysDictService sysDictService;

    @RequestMapping(value = {"/", "", "/index", "/index/"})
    public String index(ModelMap modelMap){
        Map<String, Object> filter = new HashMap<>();
        filter.put("orderBy", "sort desc");
        List<Category> categoryList = categoryService.getList(filter);
        filter.put("page", 0);
        filter.put("pageSize", 16);
        filter.put("isDelete", 0);
        filter.put("orderby", "create_time desc");
        List<Product> productList = productService.getList(filter);
        List<SysDict> storeList = sysDictService.getList("StoreSettings");
        pageData.put("category", categoryList);
        pageData.put("product", productList);
        pageData.put("store", storeList);
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

    @RequestMapping(value = "/jewellery", method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object> param,
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

}
