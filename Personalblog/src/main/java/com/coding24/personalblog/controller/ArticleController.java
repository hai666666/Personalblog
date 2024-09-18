package com.coding24.personalblog.controller;

import com.coding24.personalblog.entity.Article;
import com.coding24.personalblog.entity.Articlecomment;
import com.coding24.personalblog.entity.User;
import com.coding24.personalblog.service.AcService;
import com.coding24.personalblog.service.ArticleService;
import com.coding24.personalblog.service.SortService;
import com.coding24.personalblog.service.UsermanagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    AcService acService;
    @Autowired
    SortService sortService;
    @Autowired
    UsermanagementService usermanagementService;

    // 显示主页
    @GetMapping("/")
    public String home(Model model){
        // 获取所有文章
        List<Article> articles = articleService.getAll();
        model.addAttribute("articles", articles);
        return "home";
    }

    // 显示文章详情
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping("/articleshow/{id}")
    public ModelAndView articleshow(@PathVariable("id") Integer id) {
        Article article = articleService.queryById(id);
        //显示评论
        List<Articlecomment> articlecomments=acService.queryByArticleId(id);
        article.setArticlecomments(articlecomments);

        return new ModelAndView("articleshow", "article", article);
    }

    // 转到表单页面
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping(params = "articleform")
    public ModelAndView toForm(Article article){
        ModelAndView mv = new ModelAndView();
        mv.addObject("article",article);
        // 获取所有评论列表
        mv.addObject("sortlist", sortService.getAllSort());
        mv.setViewName("articleform");
        return mv;
    }

    // 添加或更新文章
    @Secured({"ROLE_user","ROLE_admin"})
    @PostMapping("/addOrUpdate")
    public ModelAndView addOrUpdate(Article article, RedirectAttributes redirect){
        ModelAndView mv = new ModelAndView();
        Integer aid = article.getAid();

        //获取当前用户的uid
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = usermanagementService.findUserByName(username);
        Integer uid = user.getUid();
        article.setUid(uid);

        if(aid==null){
            // 添加文章
            articleService.add(article);
            redirect.addFlashAttribute("globalArticle", "成功添加文章");
        }
        else {
            // 更新文章
            articleService.update(article);
            redirect.addFlashAttribute("globalArticle", "成功修改文章");
        }
        mv.addObject("article.aid", article.getAid());
        mv.setViewName("redirect:/articleshow/{article.aid}");
        return mv;
    }

    // 添加评论
    @Secured({"ROLE_user","ROLE_admin"})
    @PostMapping("/articleshow/{id}/addArticlecomment")
    public ModelAndView addArticlecomment(@PathVariable("id") Integer id,Articlecomment articlecomment, RedirectAttributes redirect){
        ModelAndView mv = new ModelAndView();
        articlecomment.setArticleid(id);

        //获取当前用户的uid
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = usermanagementService.findUserByName(username);
        Integer uid = user.getUid();
        articlecomment.setUid(uid);

        acService.addcomment(articlecomment);
        redirect.addFlashAttribute("globalArticle", "成功添加评论");
        mv.addObject("article.id", id);
        mv.setViewName("redirect:/articleshow/{article.id}");
        return mv;
    }

    // 删除文章
    @Secured({"ROLE_admin"})
    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        // 删除文章的评论
        acService.deletebyarticleid(id);
        // 删除文章
        articleService.delete(id);
        return "redirect:/";
    }

    // 删除评论
    @Secured({"ROLE_admin"})
    @GetMapping(value = "/deletecomment/{id}")
    public String deletecomment(@PathVariable("id") Integer id, RedirectAttributes redirect){
        int articleId =  acService.queryArticleIdByid(id);
        acService.deletebycommentid(id);
        redirect.addFlashAttribute("globalArticle", "成功删除评论");
        return "redirect:/articleshow/"+articleId;
    }

    // 更新文章表单
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping(value = "/updateForm/{id}")
    public ModelAndView updateForm(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();

        Article article = articleService.queryById(id);

        //获取当前用户的uid
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = usermanagementService.findUserByName(username);
        Integer uid = user.getUid();

        // 判断当前用户是否是文章的发布者或管理员
        if (!article.getUid().equals(uid) && !uid.equals(1)) {
            redirectAttributes.addFlashAttribute("globalArticle", "您不是该文章的发布者，不能修改该文章");
            mv.setViewName("redirect:/articleshow/"+article.getAid());
            return mv;
        }

        // 根据ID查询文章
        mv.addObject("article", articleService.queryById(id));
        // 获取所有评论列表
        mv.addObject("aclist", acService.getAllComment());
        mv.addObject("sortlist", sortService.getAllSort());
        mv.setViewName("articleform");
        return mv;
    }

    // 关于页面
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}


