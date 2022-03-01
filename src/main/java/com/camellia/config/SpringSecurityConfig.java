package com.camellia.config;

import com.camellia.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailedHandler authenticationFailedHandler;
    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomizeAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private CustomSessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter filter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManagerBean());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/hello").hasRole("user")
                .antMatchers("/admin/hello").hasRole("admin")
                .anyRequest().authenticated()
                // 登录
                .and().formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailedHandler)
                // 登出
                .and().logout().permitAll()
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JESSIONID")
                // 异常处理
                .and().exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                // 会话管理
                .and().sessionManagement()
                    .maximumSessions(1)
                    .expiredSessionStrategy(sessionInformationExpiredStrategy);
        // 过滤器
        http.addFilter(filter());
    }
}
