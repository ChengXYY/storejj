package com.cy.storejj.config;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class CommonConfig {

    @Value("${system.upload.path}")
    protected String baseSavePath;

    @Value("${system.pagesize}")
    protected Integer pageSize = 15;

    @Value("${system.session.user.account}")
    protected String userAccount;

    @Value("${system.session.user.id}")
    protected String userId = "USER_ID";

    @Value("${system.session.user.level}")
    protected String userLevel;

    @Value("${system.session.user.session}")
    protected String userSession;

    @Value("${system.session.user.vercode}")
    protected String userVercode;

    protected Map<String, Object> setPagenation(Map<String, Object> params){
        Map<String, Object> param = params;
        if(param.get("pageNum") == null || param.get("pageNum").equals("0"))
            param.put("pageNum", 1);

        Integer page = Integer.parseInt(param.get("pageNum").toString());
        if(param.get("totalCount") == null ) param.put("totalCount", 0);
        int totalCount = Integer.parseInt(param.get("totalCount").toString());
        int pSize = 0;
        if(param.get("pageSize") == null || param.get("pageSize").equals("0")){
            pSize = pageSize;
        }else{
            pSize = Integer.parseInt(param.get("pageSize").toString());
        }
        int pageCount = (int)Math.ceil((double)totalCount/pSize);
        if(pageCount <1){
            pageCount = 1;
        }
        if(page > pageCount){
            page = pageCount;
        }
        param.put("currentPage", page);
        param.put("pageSize", pSize);

        page = (page-1)*pSize;

        param.put("pageCount", pageCount);

        param.put("pageNum", page);
        return param;
    }

}
