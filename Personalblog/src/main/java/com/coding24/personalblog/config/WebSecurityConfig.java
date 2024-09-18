package com.coding24.personalblog.config;

import com.coding24.personalblog.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfig {   //SpringSecurity 5.4.x以上不用写继承语句
    @Autowired
    UserService userService;  //注入
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  //密码要加密,用了BCryptPasswordEncoder加密方式
    }//静态资源直接放行
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //忽略这些静态资源（不拦截）
        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**", "/images/**","/audio/**");
    }
    @Bean
    SecurityFilterChain filterChn(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/","/index","/public/**","/registered","/registeredform","/picture").permitAll() //允许直接访问的路径
                .anyRequest().authenticated()//其他任何请求都必须经过身份验证
        ).formLogin(formLogin -> formLogin //开启表单验证
                .loginPage("/toLogin")//跳转到自定义的登录页面
                .usernameParameter("username")//自定义表单的用户名的name,默认为username
                .passwordParameter("password")//自定义表单的密码的name,默认为password
                .loginProcessingUrl("/login")//表单请求的地址，使用Security定义好的/login，并且与自定义表单的action一致
                .failureUrl("/toLogin/error")//如果登录失败跳转到
                .permitAll() //允许访问登录有关的路径
        ).logout(logout -> logout.logoutSuccessUrl("/") //注销后跳转到首页面
        ).csrf(crsf->crsf.disable()  //关闭csrf
        ).exceptionHandling(ex->ex.accessDeniedPage("/noPermit")
        ).rememberMe(Customizer.withDefaults()
        ).userDetailsService(userService); //要加入这行
        //没有权限时跳转页
        return http.build();
    }
}