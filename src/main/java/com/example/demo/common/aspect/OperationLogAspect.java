package com.example.demo.common.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by guiqi on 2017/9/29.
 */

@Aspect
@Component
public class OperationLogAspect {

    //所有类的方法
    @Pointcut("execution(public * com.example.demo.controller.*.*(..))")
    private void allLogRecord(){
        //这个方法体将会被aspectj切面监控的方法覆盖，实际不会被执行
        System.out.println("allLogRecord.......");
    }

    //前置通知，即被切入方法执行前衩执行
    @Before("allLogRecord()")
    private void doBefore(){
        System.out.println("doBefore........");
    }

    @After("allLogRecord()")
    private void doAfter(){
        System.out.println("doAfter......");
    }

    //返回值
    @AfterReturning(returning = "result",pointcut = "execution(public * com.example.demo.controller.*.*(..))")
    private void doAfterReturn(Object result){
        System.out.println("获取目标方法返回值:" + result);
        //这种方式无法改变返回的值
        System.out.println("模拟记录日志功能...");
    }
}
