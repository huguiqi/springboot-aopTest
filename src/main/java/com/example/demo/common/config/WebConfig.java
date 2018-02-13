package com.example.demo.common.config;

import com.example.demo.controller.HelloController;
import com.example.demo.domain.RetryAdvice;
import com.example.demo.domain.SimplePojo;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.Interceptor;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Created by guiqi on 2018/1/30.
 */

@Configuration
public class WebConfig {

    @Bean
    public RetryAdvice retryAdvice(){
        return new RetryAdvice();
    }


    @Bean
    public JdkRegexpMethodPointcut regexPointcut(){
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern(".find*");
        return pointcut;
    }


    @Bean
    public DefaultPointcutAdvisor pointcutAdvisor(JdkRegexpMethodPointcut jdkRegexpMethodPointcut,RetryAdvice retryAdvice){

        return new DefaultPointcutAdvisor(jdkRegexpMethodPointcut,retryAdvice);
    }

    @Bean
    public SimplePojo createProxyFactory(DefaultPointcutAdvisor pointcutAdvisor,ApplicationContext applicationContext){


        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(pointcutAdvisor);
        proxyFactory.setTarget(new SimplePojo());

        SimplePojo proxy = (SimplePojo) proxyFactory.getProxy();
        proxy.findAll();
        return proxy;
    }
}
