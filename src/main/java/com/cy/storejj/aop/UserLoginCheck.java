package com.cy.storejj.aop;

import com.cy.storejj.config.WebConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class UserLoginCheck extends WebConfig {
    @Pointcut("within(com.cy.storejj.web.UserCenterController)")
    public void login(){}

    @Around("login()")
    public Object isLogin(ProceedingJoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();

        if(session.getAttribute(userAccount) == null){
            return webHtml+"login";
        }
        return joinPoint.proceed();
    }
}
