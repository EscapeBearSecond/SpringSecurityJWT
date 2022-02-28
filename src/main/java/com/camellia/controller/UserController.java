package com.camellia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/user/hello")
    public String userHello(){
        return "user hello";
    }
    @GetMapping("/admin/hello")
    public String adminHello(){
        return "admin hello";
    }
}
