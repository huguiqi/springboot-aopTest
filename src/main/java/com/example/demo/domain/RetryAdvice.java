package com.example.demo.domain;

import org.aopalliance.aop.Advice;
import org.springframework.aop.BeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by guiqi on 2017/9/6.
 */
public interface RetryAdvice extends BeforeAdvice {

    public void before(Object source, Object[] args, Method targetMethod);

}
