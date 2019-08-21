package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Picture;
import com.cy.storejj.service.PictureService;
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
@RequestMapping("/picture")
@Permission("2200")
public class PictureController extends AdminConfig {

    @Autowired
    private PictureService pictureService;

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

        int totalCount = pictureService.getCount(param);
        param.put("totalCount", totalCount);
        param = setPagenation(param);

        List<Picture> list = pictureService.getList(param);

        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+pictureModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "resource");
        model.addAttribute("LeftMenuFlag", "picture");
        return adminHtml +"picture_list";
    }

    @Permission("2202")
    @RequestMapping("/add")
    public String add(ModelMap model){
        //获取模板列表       
        model.addAttribute("pageTitle",addPageTitle+pictureModuleTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "resource");
        model.addAttribute("LeftMenuFlag", "picture");
        return adminHtml +"picture_add";
    }

    @Permission("2202")
    @ResponseBody
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    public JSONObject add(Picture picture, HttpSession session){
        picture.setCreateBy(session.getAttribute(adminAccount).toString());
        try {
            return pictureService.add(picture);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2203")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id")Integer id, ModelMap model){

        try {

            Picture picture = pictureService.get(id);
            model.addAttribute("picture", picture);
            model.addAttribute("pageTitle",editPageTitle+pictureModuleTitle+systemTitle);
            model.addAttribute("TopMenuFlag", "resource");
            model.addAttribute("LeftMenuFlag", "picture");

            return adminHtml +"picture_edit";
        }catch (JsonException e){
            model.addAttribute("error", e.toJson());
            return "/error/common";
        }
    }

    @Permission("2203")
    @ResponseBody
    @RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
    public JSONObject edit(Picture picture){
        try {
            return pictureService.edit(picture);

        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2204")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove(@RequestParam(value = "id")Integer id){
        try {
            return pictureService.remove(id);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @ResponseBody
    @RequestMapping("/upload")
    public JSONObject uploadIamge(@RequestParam(value = "fileupload")MultipartFile file){

        try {
            JSONObject result = CommonOperation.uploadFile(file);
            result.put("url", result.get("realname"));
            result.put("name", result.get("filename"));
            result.remove("realname");
            result.remove("filename");
            return result;
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public JSONObject get(@RequestParam(value = "code")String code){
        try {
            Picture pic = pictureService.get(code);
            return CommonOperation.success(pic);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("2205")
    @ResponseBody
    @RequestMapping("/batchremove")
    public JSONObject batchRemove(@RequestParam(value = "ids")String ids){
        try {
            return pictureService.remove(ids);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}

