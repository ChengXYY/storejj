package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Category;
import com.cy.storejj.model.Product;
import com.cy.storejj.model.ProductImages;
import com.cy.storejj.service.CategoryService;
import com.cy.storejj.service.ProductService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController extends AdminConfig {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Permission("3100")
    @RequestMapping(value = {"", "/index", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();

        currentUrl = handleParam(param, currentUrl);

        param.put("currentUrl", currentUrl);
    //    param.put("isShop", 0);
        param.put("isDelete",0);
        int totalCount = productService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<Product> list = productService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+productModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "product");
        model.addAttribute("LeftMenuFlag", "product");
        return adminHtml +"product_list";
    }

    @ResponseBody
    @RequestMapping(value = "/getimages",method = RequestMethod.POST)
    public JSONObject getImages(@RequestParam("id")Integer id){
        try {
            List<ProductImages> images = productService.getImages(id);
            if(images.size() <1 ){
                return CommonOperation.fail("暂无图片");
            }
            List<Map<String, Object>> data = new ArrayList<>();
            images.forEach(r->{
                Map<String, Object> map = new HashMap<>();
                map.put("alt", r.getName());
                map.put("src", "/getimg?filename="+r.getUrl());
                map.put("pid", r.getId());
                data.add(map);
            });
            JSONObject rs = CommonOperation.success();
            rs.put("data", data);
            return rs;
        }catch (JsonException e){
            return e.toJson();
        }
    }


    @Permission("3102")
    @RequestMapping("/add")
    public String add(ModelMap model){
        //获取模板列表
        List<Category> categories = categoryService.getList(null);
        model.addAttribute("categoryList", categories);
        model.addAttribute("pageTitle",addPageTitle+productModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "product");
        model.addAttribute("LeftMenuFlag", "product");
        return adminHtml +"product_add";
    }

    @Permission("3102")
    @ResponseBody
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    public JSONObject add(Product product, HttpSession session){
        product.setCreateBy(session.getAttribute(adminAccount).toString());
        try {
            return productService.add(product);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("3103")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true)Integer id, ModelMap model){

        try {

            Product product = productService.get(id);
            //获取模板列表
            List<Category> categories = categoryService.getList(null);
            model.addAttribute("categoryList", categories);
            model.addAttribute("product", product);
            model.addAttribute("images", product.getImages());
            model.addAttribute("pageTitle",editPageTitle+productModuleTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "product");
            model.addAttribute("LeftMenuFlag", "product");

            return adminHtml +"product_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("3103")
    @ResponseBody
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    public JSONObject edit(Product product){
        try {
            return productService.edit(product);

        }catch (JsonException e){
            return e.toJson();
        }
    }


    @Permission("3104")
    @ResponseBody
    @RequestMapping("/shop/batchadd")
    public JSONObject shopAdd(@RequestParam(value = "ids")String ids){
        try {
            return productService.add2Shop(ids, 1);
        }catch (JsonException e){
            return e.toJson();
        }
    }
    @Permission("3104")
    @ResponseBody
    @RequestMapping("/shop/batchremove")
    public JSONObject shopRemove(@RequestParam(value = "ids")String ids){
        try {
            return productService.add2Shop(ids, 0);
        }catch (JsonException e){
            return e.toJson();
        }
    }


    @Permission("3105")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove(@RequestParam(value = "id", required = true)Integer id){
        try {
            return productService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }


    @Permission("3100")
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public JSONObject get(@RequestParam(value = "code")String code){
        try {
            Product product = productService.get(code);
            return CommonOperation.success(product);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("3106")
    @ResponseBody
    @RequestMapping("/batchremove")
    public JSONObject batchRemove(@RequestParam(value = "ids")String ids){
        try {
            return productService.remove(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    //销售
    @Permission("3107")
    @RequestMapping(value = "/sell", method = RequestMethod.GET)
    public String sell(@RequestParam("id")Integer id, ModelMap model){
        //获取产品
        try{
            Product product = productService.get(id);
            model.addAttribute("product", product);
            return adminHtml +"product_sell";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    //---积分商城相关
    //兑换

    @Permission("3200")
    @RequestMapping("/shop")
    public String shopList(@RequestParam Map<String, Object> param,
                           HttpServletRequest request,
                           ModelMap model){
        String currentUrl = request.getRequestURI();
        if(param.get("code")!=null && StringUtils.isNotBlank(param.get("code").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "code", param.get("code").toString());
        }
        if(param.get("name")!=null && StringUtils.isNotBlank(param.get("name").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "name", param.get("name").toString());
        }
        param.put("currentUrl", currentUrl);
        param.put("isDelete",0);
        param.put("isShop", 1);
        int totalCount = productService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<Product> list = productService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+productModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "product");
        model.addAttribute("LeftMenuFlag", "shop");
        return adminHtml +"shop_list";
    }

    @Permission("3202")
    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public String exchange(@RequestParam("id")Integer id, ModelMap model){
        //获取产品
        try{
            Product product = productService.get(id);
            model.addAttribute("product", product);
            return adminHtml +"product_exchange";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    //---已删除产品列表
    @Permission("3300")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteList(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();
        if(param.get("code")!=null && StringUtils.isNotBlank(param.get("code").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "code", param.get("code").toString());
        }
        if(param.get("name")!=null && StringUtils.isNotBlank(param.get("name").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "name", param.get("name").toString());
        }
        param.put("currentUrl", currentUrl);

        param.put("isDelete",1);
        int totalCount = productService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<Product> list = productService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+productModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "product");
        model.addAttribute("LeftMenuFlag", "pdelete");
        return adminHtml +"product_delete_list";
    }

    //批量上架
    @Permission("3302")
    @ResponseBody
    @RequestMapping(value = "/upper", method = RequestMethod.POST)
    public JSONObject upper(@RequestParam("ids")String ids){
        try {
            return productService.restart(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }

}
