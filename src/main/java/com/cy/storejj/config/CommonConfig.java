package com.cy.storejj.config;

import java.util.Map;

public class CommonConfig {


    protected static String fileType = "picture|article|product|category";

    protected static String baseSavePath = "C:/www/upload/";

    protected static Integer pageSize = 15;


    protected Map<String, Object> setPagenation(Map<String, Object> params){
        Map<String, Object> param = params;
        if(param.get("pageNum") == null || param.get("pageNum").equals("0"))
            param.put("pageNum", 1);

        Integer page = Integer.parseInt(param.get("pageNum").toString());
        if(param.get("totalCount") == null ) param.put("totalCount", 0);
        int totalCount = Integer.parseInt(param.get("totalCount").toString());
        int pageCount = (int)Math.ceil((double)totalCount/pageSize);
        if(pageCount <1){
            pageCount = 1;
        }
        if(page > pageCount){
            page = pageCount;
        }
        param.put("currentPage", page);
        param.put("pageSize", pageSize);

        page = (page-1)*pageSize;

        param.put("pageCount", pageCount);

        param.put("pageNum", page);
        return param;
    }

}
