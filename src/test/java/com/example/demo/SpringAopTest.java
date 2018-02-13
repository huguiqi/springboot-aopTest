package com.example.demo;

import com.example.demo.domain.Pojo;
import com.example.demo.domain.RetryAdvice;
import com.example.demo.domain.SimplePojo;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

/**
 * Created by guiqi on 2017/10/20.
 */

public class SpringAopTest {


    @Test
    public void testSpringAop(){
        //1. 设置被代理对象(target)，而SimplePojo的foo方法就是切入点：Join Point
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        //2.将被代理对象的接口类添加进去(其实也可以不加,暂时还不清楚它有什么作用)
//        factory.addInterface(Pojo.class);
        //3, 添加advice拦截,拦截中的before方法就是Pointcut
        factory.addAdvice(new RetryAdvice());
            //是否暴露被代理目标类
        factory.setExposeProxy(true);//指定对外发布代理对象，即在目标对象方法中可以通过AopContext.currentProxy()访问当前代理对象。
        Pojo pojo = (Pojo) factory.getProxy();
        // 这个连接点被调用时，就会调用代理类Proxy,pojo.foo()方法被调用前会被proxy的target source所覆盖(这里新的代理类名称(target source)是：SingletonTargetSource)
        pojo.foo();
    }



    @Test
    public void testJDKPointcut(){

        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern(".find*");

        RetryAdvice advice = new RetryAdvice();
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(pointcut,advice);

        ProxyFactory proxyFactory = new ProxyFactory(new SimplePojo());
        proxyFactory.addAdvisor(defaultPointcutAdvisor);

        Pojo proxy = (Pojo) proxyFactory.getProxy();
        proxy.findAll();
    }

}
