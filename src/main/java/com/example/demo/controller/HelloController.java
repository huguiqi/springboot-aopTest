package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guiqi on 2017/7/28.
 */

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {

        return "Greetings from Spring Boot!";
    }


    @RequestMapping("/add")
    public String add() {
        System.out.println("test");
        return "add success aaa";
    }


}
