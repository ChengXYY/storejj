package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.utils.CommonOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController extends AdminConfig {

    @RequestMapping("/upload/image")
    @ResponseBody
    public JSONObject product(@RequestParam(value = "uploadfile")MultipartFile file){
        try{
            return CommonOperation.uploadFile(file, null);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @RequestMapping("/file/remove")
    @ResponseBody
    public JSONObject removeFile(@RequestParam("filename")String fileName){
        try {
            return CommonOperation.removeFile(fileName);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
