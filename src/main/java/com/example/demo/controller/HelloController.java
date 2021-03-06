package com.example.demo.controller;

import com.example.demo.domain.Pojo;
import com.example.demo.domain.SimplePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by guiqi on 2017/7/28.
 */

@RestController
public class HelloController {

    @Autowired
    private Pojo simplePojo;

    @RequestMapping("/")
    public String index() {

        return "Greetings from Spring Boot!";
    }


    @RequestMapping("/add")
    public String add() {
        System.out.println("test");
        return "add success aaa";
    }


    @RequestMapping("/findAll")
    public String findAll(){
        simplePojo.findAll();
        return "findAll";
    }


}
