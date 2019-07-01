package com.cy.storejj.version;


import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<VersionCondition>  getCustomTypeCondition(Class<?> handlerType){
        Version version = AnnotationUtils.findAnnotation(handlerType, Version.class);
        return createCondition(version);
    }

    @Override
    protected RequestCondition<VersionCondition> getCustomMethodCondition(Method method) {
        Version version = AnnotationUtils.findAnnotation(method, Version.class);
        return createCondition(version);
    }

    private RequestCondition<VersionCondition> createCondition(Version version) {
        return version == null ? null : new VersionCondition(version.value());
    }
}
