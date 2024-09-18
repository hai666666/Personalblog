package com.coding24.personalblog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/toLogin") //自定义登录页的访问路径
    public String toLogin(HttpServletRequest request){
        String msg= (String) request.getSession().getAttribute("msg");
        if(msg!=null){
            request.setAttribute("msg",msg);
        }
        return "login";  //跳转到自定义登录页login.html
    }
    @GetMapping("/noPermit")
    public String noPermit(){
        return "noPermit";  //没有权限警告页
    }
    @GetMapping("/toLogin/error") //登录失败发生异常时的访问路径
    public String tologin(HttpServletRequest request, Model model) {
        AuthenticationException authenticationException =
                (AuthenticationException) request.getSession()
                        .getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (authenticationException instanceof UsernameNotFoundException ||
                authenticationException instanceof BadCredentialsException) {
            model.addAttribute("msg", "用户名或密码错误");
        } else if (authenticationException instanceof DisabledException) {
            model.addAttribute("msg", "用户已被禁用");
        } else if (authenticationException instanceof LockedException) {
            model.addAttribute("msg", "账户被锁定");
        } else if (authenticationException instanceof
                AccountExpiredException) {
            model.addAttribute("msg", "账户过期");
        } else if (authenticationException instanceof
                CredentialsExpiredException) {
            model.addAttribute("msg", "证书过期");
        }
        return "login";  //登录失败返回登录页,并显示错误信息
    }
}