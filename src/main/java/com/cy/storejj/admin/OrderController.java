package com.cy.storejj.admin;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.aop.Permission;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.model.Order;
import com.cy.storejj.model.Product;
import com.cy.storejj.service.OrderService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController extends AdminConfig {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Permission("5100")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam Map<String, Object> param,
                       HttpServletRequest request,
                       ModelMap model){

        String currentUrl = request.getRequestURI();
        currentUrl = handleParam(param, currentUrl);

        if(param.get("productCode")!=null && StringUtils.isNotBlank(param.get("productCode").toString())){
            currentUrl = CommonOperation.setUrlParam(currentUrl, "productCode", param.get("productCode").toString());
            Product product = productService.get(param.get("productCode").toString());
            if(product != null){
                param.put("productId", product.getId());
            }
        }else {
            param.remove("productCode");
        }

        param.put("currentUrl", currentUrl);

        int totalCount = orderService.getCount(param);
        param.put("totalCount", totalCount);
        setPagenation(param);

        List<Order> list = orderService.getList(param);
        model.addAllAttributes(param);
        model.addAttribute("list", list);

        model.addAttribute("pageTitle",listPageTitle+orderMenuTitle+systemTitle);
        model.addAttribute("TopMenuFlag", "order");
        model.addAttribute("LeftMenuFlag", "order");
        return adminHtml+"order_list";
    }

    @Permission("3107")
    @RequestMapping("/sell")
    @ResponseBody
    public JSONObject sell(Order order, HttpSession session){
        try{
            String account = session.getAttribute(adminAccount).toString();
            order.setCreateBy(account);
            order.setIsCash(1); //现金支付
            return orderService.add(order);
        }catch (JsonException e){
            return e.toJson();
        }
    }

    @Permission("3202")
    @RequestMapping("/exchange")
    @ResponseBody
    public JSONObject exchange(Order order, HttpSession session){
        try{
            String account = session.getAttribute(adminAccount).toString();
            order.setCreateBy(account);
            order.setIsCash(0); //积分兑换
            return orderService.add(order);
        }catch (JsonException e){
            return e.toJson();
        }
    }
}
