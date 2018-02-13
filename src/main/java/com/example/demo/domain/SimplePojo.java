package com.example.demo.domain;

import org.springframework.stereotype.Component;

/**
 * Created by guiqi on 2017/10/20.
 */
@Component
public class SimplePojo implements Pojo {

    public void foo() {
        // this next method invocation is a direct call on the 'this' reference
        System.out.println("foo......");
        this.bar();
    }

    public void bar() {
        // some logic...
        System.out.println("bar.....");
    }

    public void findAll(){

        System.out.println("Pattern findAll");
    }
}
