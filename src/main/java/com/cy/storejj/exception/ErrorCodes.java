package com.cy.storejj.exception;

import java.util.Objects;

public enum ErrorCodes {
    //1xxxx:参数相关错误
    //2xxxx:
    //7xxxx:文件相关错误
    //8xxxx:登录、权限相关错误
    //9xxxx:统一错误码

    PARAM_NOT_EMPTY(10001,"参数不能为空"),
    ID_NOT_LEGAL(10002, "ID不合法"),
    CODE_REPEATED(10003, "编码重复"),
    PARAM_NOT_LEGAL(10004, "参数不合法"),
    ITEM_REPEATED(10005, "记录重复"),
    LEVEL_REPEATED(10006, "等级重复"),

    ITEM_NOT_EXIST(20001, "记录不存在"),
    DATA_OP_FAILED(20002, "数据操作失败"),

    GROUP_NOT_EMPTY(30001, "群组不为空"),
    PRODUCT_NOT_EXIST(30002, "产品信息不存在"),
    STOCK_NOT_ENOUGH(30003, "产品库存不足"),
    USER_NOT_EXIST(30004, "会员信息不存在"),

    //会员制度
    POINTS_CROSS_HIGN(40001, "积分交叉：最高积分>等级高的最低积分"),
    POINTS_CROSS_LOW(40002, "积分交叉：最低积分<等级低的最高积分"),

    FILE_NOT_EXSIT(70001, "文件不存在"),
    FILE_UPLOAD_FAILED(70002, "文件上传失败"),
    FILE_WRITE_FAILED(70003, "文件写失败"),
    PATH_IS_WRONG(70004, "路径不正确"),

    VERCODE_NOT_EMPTY(80001, "验证码不能为空"),
    VERCODE_IS_WRONG(80002, "验证码错误"),
    PASSWORD_NOT_EMPTY(80003, "密码不能为空"),
    PASSWORD_IS_WRONG(80004, "密码错误"),
    PASSWORD_NOT_SAME(80005, "两次密码不一致"),
    UN_LOGIN(80006, "用户未登录"),
    SYS_ACCOUNT(80007, "系统管理员账户，密码不能修改"),
    ACCOUNT_NOT_EMPTY(80008, "账户不能为空"),
    MOBILE_NOT_EMPTY(80009, "手机号不能为空"),
    SUBMIT_FAST(80010, "提交过于频繁"),
    STRING_TOO_LONG(80011, "内容过长"),

    SERVER_IS_WRONG(90001, "服务端错误"),
    HTTP_IS_WRONG(90002, "http服务错误"),

    CUSTOM_ERROR(99999, "自定义错误")
    ;

    private Integer retCode;
    private String retMsg;

    ErrorCodes(Integer retCode, String retMsg){
        this.retCode = retCode ;
        this.retMsg = retMsg;
    }

    public int getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public static ErrorCodes fromErrorCode(Integer code){
        for (ErrorCodes error : ErrorCodes.values()) {
            if (Objects.equals(code,error.getRetCode())) {
                return error;
            }
        }
        return null;
    }

}
