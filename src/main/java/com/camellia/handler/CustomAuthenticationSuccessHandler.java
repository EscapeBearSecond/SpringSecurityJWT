package com.camellia.handler;

import com.alibaba.fastjson.JSON;
import com.camellia.entity.Suser;
import com.camellia.entity.VoUser;
import com.camellia.service.UserService;
import com.camellia.util.JsonResult;
import com.camellia.util.JwtUtil;
import com.camellia.util.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        VoUser user = (VoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.getSuser().setPassword("");
        // 生成token
        String token = JwtUtil.generateToken(user.getUsername());
        response.setHeader(JwtUtil.AUTHORIZATION,token);
        JsonResult<VoUser> result = ResultTool.success(user);
        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));

    }
}
