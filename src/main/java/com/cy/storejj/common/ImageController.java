package com.cy.storejj.common;

import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Picture;
import com.cy.storejj.service.PictureService;
import com.cy.storejj.utils.CommonOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ImageController extends AdminConfig {

    @Autowired
    private PictureService pictureService;



    @RequestMapping(value = "/getimg", method = RequestMethod.GET)
    public void getImage(@RequestParam(value = "filename") String filename,
                         HttpServletRequest request, HttpServletResponse response)throws IOException {
        CommonOperation.getImage(filename, request, response);
    }

    @RequestMapping(value = {"/getpic"}, method = RequestMethod.GET)
    public void getPicture(@RequestParam(value = "code") String code,
                           HttpServletRequest request, HttpServletResponse response)throws IOException{
        try {
            Picture pic = pictureService.get(code);
            String filename = pic.getUrl();

            CommonOperation.getImage(filename,request, response);
        }catch (JsonException e){
            System.out.println(e.toJson());
        }
    }

}
