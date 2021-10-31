package com.jherrera.projectazure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Controller {

    @GetMapping("/")
    public String hello(){
        return "Hello world";
    }

    @GetMapping("/data")
    public String data(){
        return "Esto es el endpoint de data";
    }
}
