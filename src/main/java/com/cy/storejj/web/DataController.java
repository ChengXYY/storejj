package com.cy.storejj.web;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Article;
import com.cy.storejj.model.Picture;
import com.cy.storejj.service.ArticleService;
import com.cy.storejj.service.PictureService;
import com.cy.storejj.utils.CommonOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private PictureService pictureService;

    @ResponseBody
    @RequestMapping(value = {"/getArticle", "/getarticle"}, method = RequestMethod.POST)
    public JSONObject getArticle(@RequestParam(value = "code", required = true)String code){
        try {
            Article article = articleService.get(code);
            return CommonOperation.success(article);
        }catch (JsonException e){
            return e.toJson();
        }

    }

    @ResponseBody
    @RequestMapping(value = {"/getPicture", "/getpicture"}, method = RequestMethod.POST)
    public JSONObject getPicture(@RequestParam(value = "code", required = true)String code){
        try {
            Picture picture = pictureService.get(code);
            return CommonOperation.success(picture);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
