package com.coding24.personalblog.controller;

import com.coding24.personalblog.entity.Picture;
import com.coding24.personalblog.entity.Picturecomment;
import com.coding24.personalblog.entity.User;
import com.coding24.personalblog.service.PcService;
import com.coding24.personalblog.service.PictureService;
import com.coding24.personalblog.service.SortService;
import com.coding24.personalblog.service.UsermanagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class PictureController {
    @Autowired
    PictureService pictureService;
    @Autowired
    PcService pcService;
    @Autowired
    SortService sortService;
    @Autowired
    UsermanagementService usermanagementService;

    // 显示所有图片
    @GetMapping("/picture")
    public String home(Model model){
        // 获取所有图片
        List<Picture> pictures = pictureService.getAll();
        model.addAttribute("pictures", pictures);
        return "picture";
    }

    //根据提供的ID获取图片及其相关评论
    @GetMapping("/pictureshow/{id}")
    public ModelAndView pictureshow(@PathVariable("id") Integer id) {
        Picture picture = pictureService.queryById(id);
        //显示评论
        List<Picturecomment> picturecomments=pcService.queryByPictureId(id);
        picture.setPicturecomments(picturecomments);
        //System.out.println(picture.getPicturecomments());

        return new ModelAndView("pictureshow", "picture", picture);
    }

    // 转到表单页面
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping(params = "pictureform")
    public ModelAndView toForm(Picture picture){
        ModelAndView mv = new ModelAndView();
        mv.addObject("picture",picture);
        // 获取所有评论列表
        mv.addObject("sortlist", sortService.getAllSort());
        mv.setViewName("pictureform");
        return mv;
    }

    //添加图片
    @Secured({"ROLE_user","ROLE_admin"})
    @PostMapping("/addPicture")
    public ModelAndView addPicture(@RequestParam("file")MultipartFile file, Picture picture,RedirectAttributes redirect)throws IOException{
        ModelAndView mv = new ModelAndView();
        Integer pid = picture.getPid();

        //获取当前用户的uid
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = usermanagementService.findUserByName(username);
        Integer uid = user.getUid();
        picture.setUid(uid);

        // 如果是更新操作，先删除旧图片
        if(pid != null) {
            Picture oldPicture = pictureService.queryById(pid);
            if (oldPicture != null) {
                String url=oldPicture.getUrl();
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
        }

        //获取图片名
        String filename = file.getOriginalFilename();
        //获取图片后缀名
        String suffixName = filename.substring(filename.lastIndexOf("."));
        //为了避免发生图片替换，使用文件名重新生成
        filename = UUID.randomUUID() + suffixName;

        // 获取项目静态资源目录的绝对路径
        //String staticDirPath = ResourceUtils.getFile("classpath:static").getAbsolutePath();
        String staticDirPath = "src/main/resources/static";
        // 设置图片存储的目标路径
        String targetDirPath = staticDirPath + "/images";

        // 打印保存图片的路径
        //System.out.println("保存图片的路径：" + targetDirPath);

        // 确保目标目录存在，如果不存在则创建它
        Path targetDir = Paths.get(targetDirPath);
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }
        // 将文件存储到目标目录
        Path targetFilePath = targetDir.resolve(filename);
        Files.copy(file.getInputStream(), targetFilePath);

        // 复制文件到另一个目录
        String targetClassesDirPath = "target/classes/static/images";
        Path targetClassesDir = Paths.get(targetClassesDirPath);
        if (!Files.exists(targetClassesDir)) {
            Files.createDirectories(targetClassesDir);
        }
        Path targetClassesFilePath = targetClassesDir.resolve(filename);
        Files.copy(file.getInputStream(), targetClassesFilePath);

        picture.setUrl("/images/"+filename);

        if(pid==null){
            //添加
            pictureService.add(picture);
            redirect.addFlashAttribute("globalPicture", "成功添加图片");
        }else{
            //修改
            pictureService.update(picture);
            redirect.addFlashAttribute("globalPicture", "成功修改图片");
        }
        mv.addObject("picture.pid", picture.getPid());
        mv.setViewName("redirect:/pictureshow/{picture.pid}");
        return mv;
    }

    // 添加评论
    @Secured({"ROLE_user","ROLE_admin"})
    @PostMapping("/pictureshow/{id}/addPicturecomment")
    public ModelAndView addPicturecomment(@PathVariable("id") Integer id,Picturecomment picturecomment, RedirectAttributes redirect){
        ModelAndView mv = new ModelAndView();
        picturecomment.setPictureid(id);

        //获取当前用户的uid
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = usermanagementService.findUserByName(username);
        Integer uid = user.getUid();
        picturecomment.setUid(uid);

        pcService.addcomment(picturecomment);
        redirect.addFlashAttribute("globalPicture", "成功添加评论");
        mv.addObject("picture.id", id);
        mv.setViewName("redirect:/pictureshow/{picture.id}");
        return mv;
    }

    // 删除图片
    @Secured({"ROLE_admin"})
    @GetMapping(value = "/deletepicture/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirect){
        Picture picture=pictureService.queryById(id);
        //判断图片是否存在
        if (picture == null) {
            redirect.addFlashAttribute("globalPicture", "图片不存在");
            return "redirect:/picture";
        }
        // 删除图片文件
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

        // 删除图片的评论
        pcService.deletebyPictureid(id);
        // 删除图片
        pictureService.delete(id);
        return "redirect:/picture";
    }

    // 删除评论
    @Secured({"ROLE_admin"})
    @GetMapping(value = "/deletepicturecomment/{id}")
    public String deletepicturecomment(@PathVariable("id") Integer id, RedirectAttributes redirect){
        int pictureId =  pcService.queryPictureIdByid(id);
        pcService.deletebycommentid(id);
        redirect.addFlashAttribute("globalPicture", "成功删除评论");
        return "redirect:/pictureshow/"+pictureId;
    }

    // 更新图片表单
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping(value = "/updatepictureForm/{id}")
    public ModelAndView updateForm(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();

        Picture picture = pictureService.queryById(id);

        //获取当前用户的uid
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = usermanagementService.findUserByName(username);
        Integer uid = user.getUid();

        // 判断当前用户是否是图片的发布者或管理员
        if (!picture.getUid().equals(uid) && !uid.equals(1)) {
            redirectAttributes.addFlashAttribute("globalPicture", "您不是该图片的发布者，不能修改该图片");
            mv.setViewName("redirect:/pictureshow/"+picture.getPid());
            return mv;
        }

        // 根据ID查询图片
        mv.addObject("picture", pictureService.queryById(id));
        // 获取所有评论列表
        mv.addObject("pclist", pcService.getAllComment());
        mv.addObject("sortlist", sortService.getAllSort());
        mv.setViewName("pictureform");
        return mv;
    }

    // 下载图片
    @Secured({"ROLE_user","ROLE_admin"})
    @GetMapping("/downloadPicture/{id}")
    public ResponseEntity<InputStreamResource> downloadPicture(@PathVariable("id") Integer id) throws IOException {
        Picture picture = pictureService.queryById(id);
        if (picture == null) {
            return ResponseEntity.notFound().build();
        }
        String url = picture.getUrl();
        String filePath = "src/main/resources/static" + url;
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(file.length())
                .body(resource);
    }

}
