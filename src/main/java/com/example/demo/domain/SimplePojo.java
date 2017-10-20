package com.example.demo.domain;

/**
 * Created by guiqi on 2017/10/20.
 */
public class SimplePojo implements Pojo {

    public void foo() {
        // this next method invocation is a direct call on the 'this' reference
        this.bar();
    }

    public void bar() {
        // some logic...
    }
}
