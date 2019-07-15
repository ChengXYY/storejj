package com.cy.storejj.web;

import com.cy.storejj.config.WebConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopController extends WebConfig {

    @RequestMapping(value = {"", "/", "/index"})
    public String shopList(@RequestParam Map<String, Object> param){
        return webHtml+"shop";
    }

}
