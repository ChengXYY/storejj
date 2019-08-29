package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Picture;
import com.cy.storejj.model.PictureImages;
import com.cy.storejj.model.ProductImages;
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
import java.util.ArrayList;
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
        currentUrl = handleParam(param, currentUrl);
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

    private void handleImage(List<ProductImages> images, Picture picture){
        if(images != null){
            images.forEach(t->{
                if(t.getSize()!=null && t.getSize() == 0){
                    //删除多余图片
                    CommonOperation.removeFile(t.getUrl());
                }else if(StringUtils.isNotBlank(t.getName()) && StringUtils.isNotBlank(t.getUrl())){
                    //只保留第一张图片
                    if(StringUtils.isBlank(picture.getUrl())){
                        picture.setUrl(t.getUrl());
                    }else {
                        //删除多余图片
                        CommonOperation.removeFile(t.getUrl());
                    }
                }
            });
        }
    }

    @Permission("2202")
    @ResponseBody
    @RequestMapping(value = "/add/submit", method = RequestMethod.POST)
    public JSONObject add(PictureImages pictureImages, HttpSession session){

        try {
            Picture picture = new Picture();
            picture.setUrl(pictureImages.getUrl());
            picture.setCode(pictureImages.getCode());
            picture.setName(pictureImages.getName());
            picture.setIntro(pictureImages.getIntro());
            picture.setExt(pictureImages.getExt());
            picture.setSize(pictureImages.getSize());
            picture.setLink(pictureImages.getLink());

            picture.setCreateBy(session.getAttribute(adminAccount).toString());
            //图片处理
            handleImage(pictureImages.getImages(), picture);
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
            ProductImages img = new ProductImages();
            img.setName(picture.getUrl());
            img.setUrl(picture.getUrl());
            img.setId(picture.getId());
            List<ProductImages> images = new ArrayList<>();
            images.add(img);

            model.addAttribute("picture", picture);
            model.addAttribute("images", images);
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
    public JSONObject edit(PictureImages pictureImages){
        try {
            Picture picture = new Picture();
            picture.setId(pictureImages.getId());
            picture.setUrl(pictureImages.getUrl());
            picture.setCode(pictureImages.getCode());
            picture.setName(pictureImages.getName());
            picture.setIntro(pictureImages.getIntro());
            picture.setExt(pictureImages.getExt());
            picture.setSize(pictureImages.getSize());
            picture.setLink(pictureImages.getLink());

            handleImage(pictureImages.getImages(), picture);
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

