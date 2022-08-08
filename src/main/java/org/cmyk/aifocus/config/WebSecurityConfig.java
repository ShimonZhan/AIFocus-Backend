package org.cmyk.aifocus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
// 启用 Spring Security web 安全的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .regexMatchers("/test.*", "/api", "/swagger-ui.*", "/gitee", "/v3.*", "/auth.*", "/login", "/ws.*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
//                .and()
//                .formLogin()
                .and()
                .csrf()
                .disable()
                .cors();
    }

    // 密码加密
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}