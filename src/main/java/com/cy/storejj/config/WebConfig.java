package com.cy.storejj.config;

import com.cy.storejj.utils.CommonOperation;
import org.springframework.beans.factory.annotation.Value;

//后台配置
public class WebConfig extends CommonOperation {


    @Value("${system.html.web}")
    protected String webHtml;

    //页面标题
    @Value("${web.title}")
    protected String systemTitle;


}
