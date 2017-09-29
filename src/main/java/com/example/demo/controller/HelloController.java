package com.example.demo.controller;

import com.example.demo.bean.Car;
import com.example.demo.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        return "add success";
    }


}
