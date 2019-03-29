package com.atguigu.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")  //配置请求路径需要的权限
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动配置的登陆功能，效果，如果没有登陆，没有权限就会来到登陆页面
        //会生成默认登陆页面、默认注销请求/logout
        http.formLogin();
        //1、/login来到登陆页
        //2、重定向到/login?error表示登陆失败
        //3、更多详细规定

    }

    /**
     * 定义密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义认证规则
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        String password = passwordEncoder().encode("123456");
        auth.inMemoryAuthentication()
                .withUser("zhangsan").password(password).roles("VIP1", "VIP2")   //初始化一些用户
                .and()
                .withUser("lisi").password(password).roles("VIP2", "VIP3")
                .and()
                .withUser("wangwu").password(password).roles("VIP1", "VIP3");
    }

}
