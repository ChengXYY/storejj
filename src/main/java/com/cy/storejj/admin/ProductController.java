package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Product;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
@Permission("1003")
public class ProductController extends AdminConfig {

    @Autowired
    private ProductService productService;

    @Permission("2131")
    @RequestMapping(value = {"", "/index", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object> param,
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

    @Permission("2131")
    @RequestMapping("/add")
    public String add(ModelMap model){
        //获取模板列表
        model.addAttribute("pageTitle",addPageTitle+productModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "product");
        return adminHtml +"product_add";
    }

    @Permission("2131")
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

    @Permission("2131")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true)Integer id, ModelMap model){

        try {

            Product product = productService.get(id);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle",editPageTitle+productModuleTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "product");

            return adminHtml +"product_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("2131")
    @ResponseBody
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    public JSONObject edit(Product product){
        try {
            return productService.edit(product);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2131")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove(@RequestParam(value = "id", required = true)Integer id){
        try {
            return productService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }


    @Permission("2131")
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

    @Permission("2131")
    @ResponseBody
    @RequestMapping("/batchremove")
    public JSONObject batchRemove(@RequestParam(value = "ids")String ids){
        try {
            return productService.remove(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    //---积分商城相关
    @Permission("2133")
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

    @Permission("2133")
    @RequestMapping("/shop/batchadd")
    public JSONObject shopAdd(@RequestParam(value = "ids")String ids){
        try {
            return productService.add2Shop(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    //---已删除产品列表
    @Permission("2134")
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


}
