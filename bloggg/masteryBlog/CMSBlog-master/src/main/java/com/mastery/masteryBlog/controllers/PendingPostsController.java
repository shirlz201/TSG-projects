/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.controllers;

import com.mastery.masteryBlog.dtos.Category;
import com.mastery.masteryBlog.dtos.Post;
import com.mastery.masteryBlog.dtos.Tags;
import com.mastery.masteryBlog.service.CategoryService;
import com.mastery.masteryBlog.service.PostService;
import com.mastery.masteryBlog.service.TagService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author shirley
 */
@Controller
public class PendingPostsController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    PostService postService;

    @Autowired
    TagService tService;

    List<Post> staticList;

//    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";
    
    
    @GetMapping("pending-posts")
    public String displayHomePage(Model model) {
        List<Post> posts = postService.getAllPosts();
        List<Category> categories = categoryService.getAllCategories();
        List<Tags> tags = tService.getAllTags();
        staticList = postService.getAllStaticPost();
        model.addAttribute("Static", staticList);
        model.addAttribute("Tags", tags);
        model.addAttribute("Posts", posts);
        model.addAttribute("Categories", categories);
        return "pending-posts";
    }

    @GetMapping("deletePending")
    public String deletePost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        tService.deletePost(id);
        postService.deletePost(id);
        return "redirect:/pending-posts";
    }

}
