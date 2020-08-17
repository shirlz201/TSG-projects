/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.controllers;

import com.mastery.masteryBlog.dtos.Category;
import com.mastery.masteryBlog.dtos.Post;
import com.mastery.masteryBlog.service.CategoryService;
import com.mastery.masteryBlog.service.PostService;
import com.mastery.masteryBlog.service.TagService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author shirley
 */
@Controller
public class SubmitPendingController {

    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;

    List<Post> staticList;

    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";

    @GetMapping("submit-pending")
    public String editPending(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Post post = postService.getPostById(id);
        List<Category> Categories = categoryService.getAllCategories();
        staticList = postService.getAllStaticPost();
        model.addAttribute("Static", staticList);
        model.addAttribute("Categories", Categories);
        model.addAttribute("post", post);

        return "submit-pending";
    }

    @PostMapping("submitPending")
    public String confirmSubmit(HttpServletRequest request, Post submitPost, Boolean approved) {

        LocalDateTime date = LocalDateTime.now();

        submitPost.setPostId(Integer.parseInt(request.getParameter("postId")));
        submitPost.setUsername(request.getParameter("username"));
        submitPost.setDateTime(date);
        submitPost.setTitle(request.getParameter("title"));
        submitPost.setCategory(categoryService.getCategoryById(Integer.parseInt(request.getParameter("categories"))));
        submitPost.setPosting(request.getParameter("posting"));
        submitPost.setImagePath(request.getParameter("imagePath"));
        //post approval here
        if (approved != null) {
            submitPost.setApproved(approved);
        } else {
            submitPost.setApproved(false);
        }
        postService.updatePending(submitPost);

        return "redirect:/pending-posts";

    }


}
