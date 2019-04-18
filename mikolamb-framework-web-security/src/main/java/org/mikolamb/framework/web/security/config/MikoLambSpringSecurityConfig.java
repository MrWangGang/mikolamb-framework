package org.mikolamb.framework.web.security.config;

import org.mikolamb.framework.web.security.handler.MikoLambServerAccessDeniedHandler;
import org.mikolamb.framework.web.security.handler.MikoLambServerAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.annotation.Resource;


/**
 * @program: decisionsupportsystem
 * @description: spring权限框架配置
 * @author: Mr.WangGang
 * @create: 2018-08-29 17:08
 **/
public abstract class MikoLambSpringSecurityConfig {

    @Resource
    private MikoLambServerAccessDeniedHandler mikoLambServerAccessDeniedHandler;

    @Resource
    private MikoLambServerAuthenticationEntryPoint mikoLambServerAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        http.httpBasic().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.headers().disable();
        http.csrf().disable();
        http.requestCache().disable();
        http.exceptionHandling().authenticationEntryPoint(mikoLambServerAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedHandler(mikoLambServerAccessDeniedHandler);
        http.authorizeExchange().pathMatchers("/").permitAll();
        http.authorizeExchange().pathMatchers("/csrf").permitAll();
        http.authorizeExchange().pathMatchers("/v2/api-docs/**").permitAll();
        http.authorizeExchange().pathMatchers("/swagger-ui.html/**").permitAll();
        http.authorizeExchange().pathMatchers("/swagger-resources/**").permitAll();
        http.authorizeExchange().pathMatchers("/webjars/**").permitAll();
        strategy(http);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void strategy(ServerHttpSecurity http){
        http.authorizeExchange().anyExchange().authenticated();
    }
}
