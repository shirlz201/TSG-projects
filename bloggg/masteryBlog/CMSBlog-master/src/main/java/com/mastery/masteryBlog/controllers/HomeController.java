/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.controllers;

import com.mastery.masteryBlog.dtos.Category;
import com.mastery.masteryBlog.dtos.Post;
import com.mastery.masteryBlog.dtos.Tags;
import com.mastery.masteryBlog.service.PostService;
import com.mastery.masteryBlog.service.TagService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.mastery.masteryBlog.service.CategoryService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author shirley
 */
@Controller
public class HomeController {
    
    @Autowired
    CategoryService cService;
    
    @Autowired
    PostService postService;
    
    @Autowired
    TagService tService;
    
    List<Post> staticList;
    
    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";
    

    @GetMapping({"/", "/home"})
    public String displayHomePage(Model model) {
        List<Post> posts = postService.getAllPosts();
        staticList = postService.getAllStaticPost();
        model.addAttribute("Static", staticList);
        model.addAttribute("Posts", posts);
        return "home";
    }
    
    @GetMapping("deletePost")
    public String deletePost(HttpServletRequest request) {
       int id = Integer.parseInt(request.getParameter("id"));
       tService.deletePost(id);
       postService.deletePost(id);
       return "redirect:/home";
    }
    
    @PostMapping("upload") 
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:home";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            String pathString = path.toString();
            String fixedPath = pathString.substring(25);

            int postId = Integer.parseInt(request.getParameter("postId"));
            postService.addImage(fixedPath, postId);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }
    
}
