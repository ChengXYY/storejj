package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.ProductOrder;
import com.cy.storejj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/add")
    @ResponseBody
    public JSONObject sell(ProductOrder order){
        try{
            return orderService.add(order);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
