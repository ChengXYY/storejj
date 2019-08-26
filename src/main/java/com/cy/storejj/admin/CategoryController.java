package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Category;
import com.cy.storejj.service.CategoryService;
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
@RequestMapping("/category")
@Permission("3400")
public class CategoryController extends AdminConfig {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();
        currentUrl = handleParam(param, currentUrl);
        param.put("currentUrl", currentUrl);

        int totalCount = categoryService.getCount(param);
        param.put("totalCount", totalCount);
        param = setPagenation(param);
        param.put("orderby", "create_time desc");

        List<Category> list = categoryService.getList(param);

        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+categoryModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "product");
        model.addAttribute("LeftMenuFlag", "category");
        return adminHtml +"category_list";
    }

    @Permission("3402")
    @RequestMapping(value = "/add")
    public String add(ModelMap modelMap){
        modelMap.addAttribute("pageTitle",addPageTitle+categoryModuleTitle+systemTitle);
        modelMap.addAttribute("TopMenuFlag", "product");
        modelMap.addAttribute("LeftMenuFlag", "category");
        return adminHtml +"category_add";
    }

    @Permission("3402")
    @ResponseBody
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    public JSONObject add(Category category, HttpSession session){
        category.setCreateBy(session.getAttribute(adminAccount).toString());
        try {
            return categoryService.add(category);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("3403")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id")Integer id, ModelMap model){

        try {

            Category category = categoryService.get(id);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle",editPageTitle+categoryModuleTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "resource");
            model.addAttribute("LeftMenuFlag", "category");

            return adminHtml +"category_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("3403")
    @ResponseBody
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    public JSONObject edit(Category category){
        try {
            return categoryService.edit(category);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("3404")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove(@RequestParam(value = "id")Integer id){
        try {
            return categoryService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @ResponseBody
    @RequestMapping("/upload")
    public JSONObject upload(@RequestParam(value = "fileupload")MultipartFile file){

        try {
            JSONObject result = CommonOperation.uploadFile(file);
            result.put("pic", result.get("realname"));
            return result;
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
