package com.cy.storejj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import com.cy.storejj.mapper.OrderMapper;
import com.cy.storejj.model.Order;
import com.cy.storejj.model.Product;
import com.cy.storejj.model.User;
import com.cy.storejj.service.OrderService;
import com.cy.storejj.service.ProductService;
import com.cy.storejj.service.UserService;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public JSONObject add(Order sell) {
        //参数检查
        if(sell.getCount()== null || sell.getCount()<1
                || sell.getPrice()== null || sell.getPrice() <0
                || !CommonOperation.checkId(sell.getProductId())){
            throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        }
        //查找产品信息
        Product product = productService.get(sell.getProductId());
        if(product == null)throw JsonException.newInstance(ErrorCodes.PRODUCT_NOT_EXIST);
        if(product.getStock() < sell.getCount())throw JsonException.newInstance(ErrorCodes.STOCK_NOT_ENOUGH);

        if(sell.getIsCash() == 0 && StringUtils.isBlank(sell.getUserAccount())) throw JsonException.newInstance(ErrorCodes.USER_NOT_EXIST);
        User user = userService.get(sell.getUserAccount());
        if(sell.getIsCash() == 0 && user == null) throw JsonException.newInstance(ErrorCodes.USER_NOT_EXIST);

        //参数补全
        int amount = sell.getCount()*sell.getPrice();
        sell.setAmount(amount);

        String code = CommonOperation.getRandomStr(20, "DPZB");
        sell.setCode(code);

        int rs =  orderMapper.insertSelective(sell);
        if(rs > 0){
            //会员积分
            if(user != null){
                if(sell.getIsCash() == 1){ //消费现金，积分增长
                    int points = user.getPoints()+amount;
                    int psum = user.getPointsSum()+amount;
                    user.setPoints(points);
                    user.setPointsSum(psum);
                    userService.edit(user);
                }else if(sell.getIsCash() == 0){ //积分兑换，积分减少
                    int points = user.getPoints()-amount;
                    user.setPoints(points);
                    userService.edit(user);
                }
            }
            //商品库存
            int stock = product.getStock()-sell.getCount();
            product.setStock(stock);
            productService.edit(product);

            return CommonOperation.success(code);
        }else
            throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
    }

    @Override
    public JSONObject edit(Order sell) {
        return null;
    }

    @Override
    public JSONObject remove(String code) {
        return null;
    }

    @Override
    public JSONObject remove(Integer id) {
        return null;
    }

    @Override
    public Order get(String code) {
        if(StringUtils.isBlank(code)) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);
        return orderMapper.selectByCode(code);
    }

    @Override
    public Order get(Integer id) {
        if(CommonOperation.checkId(id))throw JsonException.newInstance(ErrorCodes.ID_NOT_LEGAL);
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> getList(Map<String, Object> filter) {
        return orderMapper.selectByFilter(filter);
    }

    @Override
    public Integer getCount(Map<String, Object> filter) {
        return orderMapper.countByFilter(filter);
    }



}
