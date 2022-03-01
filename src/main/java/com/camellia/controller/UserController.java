package com.camellia.controller;

import com.camellia.entity.Suser;
import com.camellia.entity.VoUser;
import com.camellia.service.UserService;
import com.camellia.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public JsonResult register(Suser suser){
        return userService.register(suser);
    }
}
