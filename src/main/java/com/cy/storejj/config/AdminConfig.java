package com.cy.storejj.config;

import com.cy.storejj.model.Product;
import com.cy.storejj.utils.CommonOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

//后台配置
public class AdminConfig extends CommonOperation{
    @Value("${system.html.admin}")
    protected String adminHtml;

    @Value("${system.session.admin.account}")
    protected String adminAccount;

    @Value("${system.session.admin.group}")
    protected String adminGroup;

    @Value("${system.session.admin.id}")
    protected String adminId;

    @Value("${system.session.admin.auth}")
    protected String adminAuth;

    @Value("${system.session.admin.session}")
    protected String adminSession;

    @Value("${system.session.admin.vercode}")
    protected String adminVercode;

    @Value("${system.account}")
    protected String sysAccount;

    @Value("${system.password}")
    protected String sysPassword;

    //title
    protected static String systemTitle = "后台管理系统";

    protected static String systemMenuTitle = "系统配置-";

    protected static String adminModuleTitle = "管理员-";

    protected static String admingroupModuleTitle = "管理员组-";

    protected static String adminlogModuleTitle = "系统日志-";

    protected static String membershipModuleTitle = "会员制度-";

    protected static String storeModuleTitle = "店铺配置-";

    protected static String noticeModuleTitle = "通知配置-";

    protected static String serviceModuleTitle = "服务配置-";

    protected static String siteMenuTitle = "网页配置-";

    protected static String sitepageModuleTitle = "页面管理-";

    protected static String pagetplModuleTitle = "模板管理-";

    protected static String resourceMenuTitle = "资料管理-";

    protected static String articleModuleTitle = "文章-";

    protected static String pictureModuleTitle = "图片-";

    protected static String productMenuTitle = "产品管理-";

    protected static String productModuleTitle = "产品-";

    protected static String categoryModuleTitle = "分类-";

    protected static String userMenuTitle = "会员管理-";

    protected static String orderMenuTitle = "订单管理-";

    protected static String suggestionMenuTitle = "用户意见-";

    protected static String surveyMenuTitle = "问卷-";

    protected static String questionMenuTitle = "问题库-";


    protected static String indexModuleTitle = "首页-";

    protected static String listPageTitle  = "列表-";

    protected static String addPageTitle = "新增-";

    protected static String editPageTitle = "编辑-";

    protected static String authPageTitle = "权限配置-";


    protected String handleParam(Map<String, Object> param, String currentUrl){
        // code
        if(param.get("code")!=null && StringUtils.isNotBlank(param.get("code").toString())){
            currentUrl = setUrlParam(currentUrl, "code", param.get("code").toString());
        }else {
            param.remove("code");
        }
        // title
        if(param.get("title")!=null && StringUtils.isNotBlank(param.get("title").toString())){
            currentUrl = setUrlParam(currentUrl, "title", param.get("title").toString());
        }else {
            param.remove("title");
        }
        // isCash
        if(param.get("isCash")!=null){
            currentUrl = setUrlParam(currentUrl, "isCash", param.get("isCash").toString());
            int isCash = Integer.parseInt(param.get("isCash").toString());
            if(isCash != 0  && isCash != 1){
                param.remove("isCash");
            }
        }else {
            param.remove("isCash");
        }
        // content
        if(param.get("content")!=null && StringUtils.isNotBlank(param.get("content").toString())){
            currentUrl = setUrlParam(currentUrl, "content", param.get("content").toString());
        }else {
            param.remove("content");
        }
        // name
        if(param.get("name")!=null && StringUtils.isNotBlank(param.get("name").toString())){
            currentUrl = setUrlParam(currentUrl, "name", param.get("name").toString());
        }else {
            param.remove("name");
        }
        // startTime
        if(param.get("startTime")!=null && StringUtils.isNotBlank(param.get("startTime").toString())){
            currentUrl = setUrlParam(currentUrl, "startTime", param.get("startTime").toString());
            String st = param.get("startTime").toString()+" 00:00:00";
            param.put("startTime", st);
        }else {
            param.remove("startTime");
        }
        // endTime
        if(param.get("endTime")!=null && StringUtils.isNotBlank(param.get("endTime").toString())){
            currentUrl = setUrlParam(currentUrl, "endTime", param.get("endTime").toString());
            String et = param.get("endTime").toString()+" 23:59:59";
            param.put("endTime", et);
        }else {
            param.remove("endTime");
        }
        return currentUrl;
    }


}
