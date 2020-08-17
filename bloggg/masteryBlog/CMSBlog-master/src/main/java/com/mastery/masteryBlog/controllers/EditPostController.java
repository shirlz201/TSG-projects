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
import java.security.Principal;
import java.util.ArrayList;
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
public class EditPostController {

    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;

    List<Post> staticList;

    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";

    @GetMapping("editPost")
    public String editPost(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Post post = postService.getPostById(id);
        List<Category> Categories = categoryService.getAllCategories();
        staticList = postService.getAllStaticPost();
        model.addAttribute("Static", staticList);
        model.addAttribute("Categories", Categories);
        model.addAttribute("post", post);

        return "editPost";
    }

    @PostMapping("editPost")
    public String confirmEditPost(HttpServletRequest request, Post editPost, Principal principal) {
        //principal: interface, can be used to represent an entity or login id
        String Username = principal.getName();
        //set each field for the post obj
        editPost.setPostId(Integer.parseInt(request.getParameter("postId")));
        editPost.setUsername(Username);
        editPost.setTitle(request.getParameter("title"));
        editPost.setCategory(categoryService.getCategoryById(Integer.parseInt(request.getParameter("categories"))));
        editPost.setPosting(request.getParameter("posting"));

        postService.updatePost(editPost);

        return "redirect:/home";

    }

    @PostMapping("editUpload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:editPost";
        }

        try {
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

        return "redirect:/editPost";
    }

}
