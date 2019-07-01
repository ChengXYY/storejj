package com.cy.storejj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.cy.storejj.mapper")
@EnableAspectJAutoProxy(exposeProxy=true)
public class StorejjApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorejjApplication.class, args);
    }

}
