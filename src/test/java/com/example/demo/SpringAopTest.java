package com.example.demo;

import com.example.demo.domain.Pojo;
import com.example.demo.domain.RetryAdvice;
import com.example.demo.domain.RetryAdviceImpl;
import com.example.demo.domain.SimplePojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by guiqi on 2017/10/20.
 */

@RunWith(SpringRunner.class)
public class SpringAopTest {


    @Test
    public void testSpringAop(){

        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addInterface(Pojo.class);
        factory.addAdvice(new RetryAdviceImpl());

        Pojo pojo = (Pojo) factory.getProxy();

        // this is a method call on the proxy!
        pojo.foo();
    }

}
