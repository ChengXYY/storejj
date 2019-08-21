package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Article;
import com.cy.storejj.service.ArticleService;
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
@RequestMapping("/article")
@Permission("2100")
public class ArticleController extends AdminConfig {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = {"", "/index", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){
        String currentUrl = request.getRequestURI();
        if(param.get("code")!=null && StringUtils.isNotBlank(param.get("code").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "code", param.get("code").toString());
        }
        if(param.get("title")!=null && StringUtils.isNotBlank(param.get("title").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "title", param.get("title").toString());
        }
        param.put("currentUrl", currentUrl);

        int totalCount = articleService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<Article> list = articleService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+articleModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "resource");
        model.addAttribute("LeftMenuFlag", "article");
        return adminHtml +"article_list";
    }

    @Permission("2102")
    @RequestMapping("/add")
    public String add(ModelMap model){
        //获取模板列表
        model.addAttribute("pageTitle",addPageTitle+articleModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "resource");
        model.addAttribute("LeftMenuFlag", "article");
        return adminHtml +"article_add";
    }

    @Permission("2102")
    @ResponseBody
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    public JSONObject add(Article article, HttpSession session){
        article.setCreateBy(session.getAttribute(adminAccount).toString());
        try {
            return articleService.add(article);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2103")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id", required = true)Integer id, ModelMap model){

        try {

            Article article = articleService.get(id);
            model.addAttribute("article", article);
            model.addAttribute("pageTitle",editPageTitle+articleModuleTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "resource");
            model.addAttribute("LeftMenuFlag", "article");

            return adminHtml +"article_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("2103")
    @ResponseBody
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    public JSONObject edit(Article article){
        try {
            return articleService.edit(article);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2104")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove(@RequestParam(value = "id", required = true)Integer id){
        try {
            return articleService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @ResponseBody
    @RequestMapping("/upload")
    public JSONObject uploadIamge(@RequestParam(value = "fileupload")MultipartFile file){

        JSONObject result = new JSONObject();
        try {
            result = CommonOperation.uploadFile(file);
            result.put("path", "/getimg?filename="+result.get("realname"));
        }catch (JsonException e){
            result = e.toJson();
        }
        return  result;
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public JSONObject get(@RequestParam(value = "code")String code){
        try {
            Article article = articleService.get(code);
            return CommonOperation.success(article);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2105")
    @ResponseBody
    @RequestMapping("/batchremove")
    public JSONObject batchRemove(@RequestParam(value = "ids")String ids){
        try {
            return articleService.remove(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
