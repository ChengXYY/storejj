package com.cy.storejj.aop;

import com.cy.storejj.config.WebConfig;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserPermissionCheck extends WebConfig {
    @Pointcut("within(com.cy.storejj.web.UserCenterController)")
    public void login(){}

    @ResponseBody
    @Around("login()")
    public Object isLogin(ProceedingJoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes attributes =   (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session= request.getSession();
        if(session.getAttribute(userAccount) == null || StringUtils.isBlank(session.getAttribute(userAccount).toString())){
            return "/userlogin";
        }

        return joinPoint.proceed();
    }

}
