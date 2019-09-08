package com.cy.storejj.config;

import java.util.Map;

public class CommonConfig {

    protected static String baseSavePath = "E:/www/upload/";

    protected static Integer pageSize = 15;

    protected static String userAccount = "USER_ACCOUNT";

    protected static String userId = "USER_ID";

    protected static String userLevel = "USER_LEVEL";

    protected static String userSession = "USER_SESSION";

    protected static String userVercode = "USER_VERCODE";

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
