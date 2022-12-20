package com.ural.security.firstsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "testController")
public class TestController {


    @GetMapping("test")
    public String testController(){

        return "test";

    }
}
