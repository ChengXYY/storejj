package com.cy.storejj.config;

import java.util.*;

public enum AuthCode {

   // Menu_Index(8001,"菜单查看权限-首页"),

    //一级菜单： topmenu
    Menu_System(9001, "菜单查看权限-系统设置"),
    Menu_SitePage(1001,"菜单查看权限-网页配置"),
    Menu_Resource(1002,"菜单查看权限-资料管理"),
    Menu_Product(1003,"菜单查看权限-产品管理"),
    Menu_Membership(1004, "菜单查看权限-会员管理"),

    //二级菜单：leftmenu
    Sys_Admin(2901, "系统权限-管理员管理"),
    Sys_AdminGroup(2902, "系统权限-管理员组管理"),
    Sys_AdminAuth(2903, "系统权限-管理员权限配置"),
    Sys_AdminLog(2904, "系统权限-管理员日志"),

    Site_Page(2111, "网页配置-页面管理"),
    Site_Tpl(2112, "网页配置-模板管理"),

    Rsrc_Article(2121, "资料管理-文章"),
    Rsrc_Picture(2122, "资料管理-图片"),

    Prdt_Product(2131, "产品管理-产品"),
    Prdt_Category(2132, "产品管理-分类"),
    Prdt_Shop(2133, "产品管理-积分商城"),

    User_List(2141, "会员管理-列表"),
    User_Add(2142, "会员管理-新增"),
    User_Level_Check(2143, "会员管理-会员等级查看"),
    User_Level_Op(2144, "会员管理-会员等级操作"),
    User_Points_Check(2145, "会员管理-会员积分查看"),
    User_Points_Op(2146, "会员管理-会员积分操作"),
    User_Info_Check(2147, "会员管理-会员详细信息查看"),
    User_Edit(2148, "会员管理-会员信息编辑"),
    ;

    private Integer code;
    private String intro;

    AuthCode(Integer code, String intro){
        this.code = code;
        this.intro = intro;
    }

    public Integer getCode(){
        return this.code;
    }
    public String getIntro(){
        return this.intro;
    }
    public static AuthCode fromAuthCode(Integer code){
        for (AuthCode codes : AuthCode.values()) {
            if (Objects.equals(code,codes.getCode())) {
                return codes;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> listAuthCode(){
        List<Map<String, Object>> result = new ArrayList<>();
        for (AuthCode codes : AuthCode.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("code", codes.getCode());
            item.put("intro", codes.getIntro());
            result.add(item);
        }
        return result;
    }

    public static String getAuthString(){
        String rs = "";
        for (AuthCode codes : AuthCode.values()) {
            rs += codes.getCode()+"|";
        }
        rs.substring(0, rs.length()-1);
        return  rs;
    }
}
