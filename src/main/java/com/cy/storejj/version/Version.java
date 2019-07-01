package com.cy.storejj.version;

import org.springframework.web.bind.annotation.Mapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Version {
    int value();
}

