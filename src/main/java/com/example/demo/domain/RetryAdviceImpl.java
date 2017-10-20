package com.example.demo.domain;

import java.lang.reflect.Method;

/**
 * Created by guiqi on 2017/10/20.
 */
public class RetryAdviceImpl implements RetryAdvice {

    @Override
    public void before(Object source, Object[] args, Method targetMethod) {
        System.out.println("before......");
    }
}
