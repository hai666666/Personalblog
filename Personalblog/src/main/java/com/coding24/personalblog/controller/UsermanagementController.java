package com.coding24.personalblog.controller;

import com.coding24.personalblog.entity.Picture;
import com.coding24.personalblog.entity.User;
import com.coding24.personalblog.entity.UserRole;
import com.coding24.personalblog.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

@Controller
@Slf4j
public class UsermanagementController {
    @Autowired
    UsermanagementService usermanagementService;
    @Autowired
    ArticleService articleService;
    @Autowired
    AcService acService;
    @Autowired
    PictureService pictureService;
    @Autowired
    PcService pcService;

    //注册报表
    @GetMapping("/registeredform")
    public ModelAndView toForm(User user){
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.setViewName("registeredform");
        return mv;
    }

    //注册用户
    @PostMapping("/registered")
    public ModelAndView registered(User user, RedirectAttributes redirect){
        ModelAndView mv = new ModelAndView();

        // 查找用户名是否已存在
        User existingUser = usermanagementService.findUserByName(user.getUsername());
        if(existingUser != null){
            redirect.addFlashAttribute("message","用户名已存在");
            mv.setViewName("redirect:/registeredform");
            return mv;
        }


        UserRole userRole = new UserRole();
        userRole.setRid(2);

        usermanagementService.add(user);

        User newuser = usermanagementService.findUserByName(user.getUsername());

        Integer uid = newuser.getUid();
        userRole.setUid(uid);

        usermanagementService.addUR(userRole);
        redirect.addFlashAttribute("message","注册成功");

        mv.setViewName("redirect:/toLogin");

        return mv;
    }

    // 显示所有用户
    @Secured({"ROLE_admin"})
    @GetMapping("/showallusers")
    public ModelAndView showAllUsers() {
        ModelAndView mv = new ModelAndView();
        List<User> userList = usermanagementService.getAllUsers(); // 假设有一个从数据库获取所有用户的方法
        mv.addObject("userList", userList);
        mv.setViewName("showallusers"); // 名为showallusers的视图来显示所有用户
        return mv;
    }

    //根据id显示单个用户
    @GetMapping("/showuser/{id}")
    public ModelAndView showuser1(@PathVariable("id") Integer id) {
        User user = usermanagementService.findUserById(id);
        return new ModelAndView("showuser", "user", user);
    }

    //根据用户名显示单个用户
    @GetMapping("/showuser")
    public ModelAndView showuser2() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = usermanagementService.findUserByName(username);
        return new ModelAndView("showuser", "user", user);
    }

    //更新用户报表
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping(value = "/modifyuserform/{id}")
    public ModelAndView modifyuserform(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",usermanagementService.findUserById(id));
        mv.setViewName("modifyuserform");
        return mv;
    }

    //管理员更新用户报表
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping(value = "/adminmodifyuserform/{id}")
    public ModelAndView adminmodifyuserform(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",usermanagementService.findUserById(id));
        mv.setViewName("adminmodifyuserform");
        return mv;
    }

    //更新用户
    @Secured({"ROLE_user","ROLE_admin"})
    @PostMapping("/modifyuser")
    public ModelAndView modifyuser(User user, RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();

        // 查找用户名是否已存在
        User existingUser = usermanagementService.findUserByName(user.getUsername());
        if(existingUser != null && !existingUser.getUid().equals(user.getUid())){
            redirect.addFlashAttribute("message","用户名已存在");
            mv.setViewName("redirect:/modifyuserform/" + user.getUid());
            return mv;
        }

        // 更新用户
        usermanagementService.updateUser(user);
        redirect.addFlashAttribute("message","已修改成功，并登录用户");
        //mv.addObject("user.uid", user.getUid());

        // 强制用户注销
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        mv.setViewName("redirect:/showuser/"+user.getUid());
        return mv;
    }

    //管理员更新用户
    @Secured({"ROLE_user","ROLE_admin"})
    @PostMapping("/adminmodifyuser")
    public ModelAndView adminmodifyuser(User user, RedirectAttributes redirect){
        ModelAndView mv = new ModelAndView();

        // 查找用户名是否已存在
        User existingUser = usermanagementService.findUserByName(user.getUsername());
        if(existingUser != null && !existingUser.getUid().equals(user.getUid())){
            redirect.addFlashAttribute("message","用户名已存在");
            mv.setViewName("redirect:/modifyuserform/" + user.getUid());
            return mv;
        }

        // 更新用户
        usermanagementService.updateUser(user);
        redirect.addFlashAttribute("message","已修改成功");
        //mv.addObject("user.uid", user.getUid());

        // 强制用户注销
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //if (auth != null){
        //    new SecurityContextLogoutHandler().logout(request, response, auth);
        //}

        mv.setViewName("redirect:/showallusers");
        return mv;
    }

    //删除用户
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping(value = "/deleteuser/{id}")
    public String deleteuser(@PathVariable("id") Integer id, RedirectAttributes redirect){
        //删除文章用户评论
        acService.deletebyuserid(id);
        //删除用户文章
        articleService.deletebyuserid(id);
        //删除用户图片评论
        pcService.deletebyuserid(id);

        Picture picture=pictureService.queryByuserId(id);
        if (picture != null) {
            String url = picture.getUrl();
            String Path1 = "src/main/resources/static";
            String Path2 = "target/classes/static";
            String filePath1 = Path1 + url;
            String filePath2 = Path2 + url;
            File file1 = new File(filePath1);
            File file2 = new File(filePath2);
            if (file1.exists()) {
                file1.delete();
            }
            if (file2.exists()) {
                file2.delete();
            }
        }
        //删除用户图片
        pictureService.deletebyuserid(id);

        usermanagementService.deleteUserRole(id);
        usermanagementService.deleteUser(id);
        redirect.addFlashAttribute("message","删除成功");
        return "redirect:/showallusers";
    }

}
