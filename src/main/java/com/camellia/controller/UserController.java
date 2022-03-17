package com.camellia.controller;

import com.camellia.entity.Suser;
import com.camellia.entity.VoUser;
import com.camellia.service.UserService;
import com.camellia.util.JsonResult;
import com.camellia.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/register")
    public JsonResult register(Suser suser) {
        return userService.register(suser);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_admin')")
    // @Secured(value = "ROLE_admin")验证码
    @GetMapping("/test")
    public String test(){
        return "hello admin";
    }
    @GetMapping("/code")
    public String code(){
        String code = UUID.randomUUID().toString();
        redisUtil.set("code",code,60);
        return "生成验证码";
    }
    @Secured(value = {"ROLE_user","ROLE_admin"})
    @GetMapping("/info")
    public JsonResult getUsers(String id){
        return userService.getUserInfo(id);
    }
}