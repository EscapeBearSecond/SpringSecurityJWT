package com.camellia.config;

import com.alibaba.fastjson.JSON;
import com.camellia.util.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        if (Arrays.asList(Constants.WHITE_LISTS).contains(requestURI)){
            // 白名单放行
            chain.doFilter(request,response);
            return;
        }
        String token = request.getHeader(JwtUtil.AUTHORIZATION);
        JsonResult result = null;
        if (token == null || token.isEmpty() || !token.startsWith(JwtUtil.TOKEN_PREFIX)){
            result = ResultTool.fail(ResultCode.HAS_NO_TOKEN);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(result));
            logger.error("没有登录或没有令牌 ");
        }else {
            try {
                UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }catch (ExpiredJwtException e) {
                result = ResultTool.fail(ResultCode.TOKEN_HAS_EXPIRED);
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(result));
                logger.error("Token已过期: {} " + e);

            } catch (UnsupportedJwtException e) {
                result = ResultTool.fail(ResultCode.TOKEN_FORMAT_ERROR);
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(result));
                logger.error("Token格式错误: {} " + e);

            } catch (MalformedJwtException e) {
                result = ResultTool.fail(ResultCode.TOKEN_FORMAT_ERROR);
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(result));
                logger.error("Token没有被正确构造: {} " + e);

            } catch (SignatureException e) {
                result = ResultTool.fail(ResultCode.SIGNATURE_ERROR);
                logger.error("签名失败: {} " + e);

            } catch (IllegalArgumentException e) {
                result = ResultTool.fail(ResultCode.TOKEN_ILLEGAL_ARGUMENT);
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(result));
                logger.error("非法参数异常: {} " + e);

            }catch (Exception e){
                result = ResultTool.fail(ResultCode.TOKEN_INVALID);
                response.setContentType("text/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(result));
                logger.error("Invalid Token " + e.getMessage());
            }

        }

    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader(JwtUtil.AUTHORIZATION);
        if (token != null) {

            try {
                // 解密Token
                String userName = JwtUtil.validateToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if (!org.springframework.util.StringUtils.isEmpty(userName)) {
                    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                }
            }catch (ExpiredJwtException e) {
                throw e;
                //throw new TokenException("Token已过期");
            } catch (UnsupportedJwtException e) {
                throw e;
                //throw new TokenException("Token格式错误");
            } catch (MalformedJwtException e) {
                throw e;
                //throw new TokenException("Token没有被正确构造");
            } catch (SignatureException e) {
                throw e;
                //throw new TokenException("签名失败");
            } catch (IllegalArgumentException e) {
                throw e;
                //throw new TokenException("非法参数异常");
            }catch (Exception e){
                throw e;
                //throw new IllegalStateException("Invalid Token. "+e.getMessage());
            }
            return null;
        }
        return null;
    }
}
