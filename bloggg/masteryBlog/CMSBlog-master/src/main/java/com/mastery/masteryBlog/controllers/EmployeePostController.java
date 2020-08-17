/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.controllers;

import com.mastery.masteryBlog.dtos.Category;
import com.mastery.masteryBlog.dtos.Post;
import com.mastery.masteryBlog.dtos.Role;
import com.mastery.masteryBlog.dtos.Tags;
import com.mastery.masteryBlog.dtos.User;
import com.mastery.masteryBlog.service.PostService;
import com.mastery.masteryBlog.service.TagService;
import com.mastery.masteryBlog.service.UserDetailsServiceImpl;
import com.mastery.masteryBlog.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.mastery.masteryBlog.service.CategoryService;
import com.mastery.masteryBlog.service.RoleService;
import java.time.LocalDateTime;
import java.util.HashSet;

/**
 *
 * @author shirley
 */
@Controller
public class EmployeePostController {

    @Autowired
    UserDetailsServiceImpl service;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    TagService tagService;

    List<Post> staticList;

    @GetMapping("/employee-post")
    public String showPage(Model model) {
        List<Category> Categories = categoryService.getAllCategories();
        staticList = postService.getAllStaticPost();
        model.addAttribute("Static", staticList);
        model.addAttribute("Categories", Categories);
        return "employee-post";
    }

    @PostMapping("addEmployeePost")
    public String addPost(HttpServletRequest request, Post post, Principal principal) {
        /* 
        A subset of subject that is represented by an account, role or other 
        unique identifier -principals are the unique keys we use in 
        access control lists.       
         */
        String Username = principal.getName();
        User user = userService.getUserByUsername(Username);

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByRoleName("ROLE_ADMIN"));
        roles.add(roleService.getRoleByRoleName("ROLE_USER"));

        String tags = request.getParameter("tags");
        String[] tagList = tags.split(",");
        List<Tags> tag = new ArrayList<>();

        LocalDateTime date = LocalDateTime.now();

        post.setUsername(Username);
        post.setTitle(request.getParameter("title"));
        post.setDateTime(date);
        post.setCategory(categoryService.getCategoryById(Integer.parseInt(request.getParameter("categories"))));
        post.setPosting(request.getParameter("posting"));
        post.setTags(tag);

        if (user.getRoles().equals(roles)) {
            post.setApproved(true);
        } else {
            post.setApproved(false);
        }

        postService.addPost(post);

        for (String newTag : tagList) {

            Tags setTag = new Tags(newTag);
            tagService.addTags(setTag);
            tag.add(tagService.getTagsById(setTag.getId()));
            tagService.createPostTag(post.getPostId(), setTag.getId());

        }

        return "redirect:/employee-post";

    }

}
