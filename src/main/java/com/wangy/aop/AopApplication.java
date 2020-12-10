package com.wangy.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 使用基于注解的aop时，必须开启&#64;{@link EnableAspectJAutoProxy}注解
 * @author wangy325
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class AopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }

}
