package com.cy.storejj.aop;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.service.AdminLogService;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;

@Aspect
@Component
public class OperationLog extends AdminConfig {

    @Pointcut("within(com.cy.storejj.admin.LoginController)")
    public void loginLog(){}

    @Pointcut("execution(public * com.cy.storejj.service.*.*(..))"+
            "&& !execution(public * com.cy.storejj.service.AdminLogService.*(..))")
    public void operationLog(){}

    @Autowired
    private AdminLogService adminlogService;

    @Around("loginLog()")
    public Object addloginlog(ProceedingJoinPoint joinPoint)throws Throwable{
        Object result = joinPoint.proceed();
        //获取访问信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddr = request.getRemoteAddr();
        if(ipAddr.startsWith("0.")){
            ipAddr = "::1";
        }
        HttpSession session = ((HttpServletRequest) request).getSession();
        if(session.getAttribute(adminAccount)==null){
            return result;
        }
        //获取访问目标方法
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String methodName = signature.getName();
        switch (methodName){
            case "login": //登录
                adminlogService.add(session, "管理员登录");
                break;
            case "logout": //登出
                if(session.getAttribute(adminAccount)!=null)
                    adminlogService.add(session, "管理员退出登录");
                break;
            default:
                break;
        }
        return result;
    }

    @Around("operationLog()")
    public Object addoplog(ProceedingJoinPoint joinPoint)throws Throwable{
        Object result = joinPoint.proceed();

        //获取访问信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = ((HttpServletRequest) request).getSession();
        //获取访问目标方法
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String methodName = signature.getName();
        String className = joinPoint.getTarget().getClass().getName().replace("ServiceImpl", "");
        String modelName = className.replace("service.serviceimpl", "model");
        className = className.substring(className.indexOf("serviceimpl")+12, className.length());

        //获取参数
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();

        //获取返回值
        String param = "";
        String checkMethodStr = "add,edit,remove";
        if(!checkMethodStr.contains(methodName)){
            return result;
        }
        if(checkMethodStr.contains(methodName) && result==null ){
            JSONObject rs = JSONObject.parseObject(result.toString());
            if(!rs.get("code").equals("0"))
                return result;
        }
        JSONObject res = JSONObject.parseObject(result.toString());

        switch (methodName){
            case "add":

                adminlogService.add(session, "添加【"+className+"】记录("+res.get("id")+")");
                break;
            case "edit":

                adminlogService.add(session, "修改【"+className+"】记录("+res.get("id")+")");
                break;
            case "remove":

                adminlogService.add(session, "删除【"+className+"】记录("+res.get("id")+")");
                break;
        }
        return result;
    }

    private String getParamValue(String paramName, Object[] args, String[] paramNames){

        int index = ArrayUtils.indexOf(paramNames, paramName);
        if(index >-1){
            return args[index].toString();
        }
        return null;
    }
}
