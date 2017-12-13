package com.example.demo.domain;

import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by guiqi on 2017/10/20.
 */
public class RetryAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        if (method.getName().equals("foo")){
            System.out.println("check password");
        }
    }
}
