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
import com.mastery.masteryBlog.service.RoleService;
import com.mastery.masteryBlog.service.UserService;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author shirley
 */
@Controller
public class AdminPostController {

    //Autowired all the service impls
    @Autowired
    UserDetailsServiceImpl service;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PostService postService;

    @Autowired
    TagService tagService;
    //global variable of static posts
    List<Post> staticList;

    /*The ConstraintViolation object holds information about the error; 
specifically, each one will hold the message of a validation error it found.*/
    Set<ConstraintViolation<Post>> violations = new HashSet<>();

    /**
     * This method will take in a model object which will be used to retrieve
     * the static posts and categories
     *
     * @param model
     * @return refer to admin-post page
     */
    @GetMapping("admin-post")
    public String displayPage(Model model) {
        List<Category> Categories = categoryService.getAllCategories();
        staticList = postService.getAllStaticPost();
        model.addAttribute("Static", staticList);
        model.addAttribute("errors", violations);
        model.addAttribute("Categories", Categories);
        return "admin-post";
    }

    /**
     * This method will take in a HTTPrequest and model that will be used to
     * retrieve the postId and static posts which will display to the static
     * section
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("static")
    public String displayStatic(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Post post = postService.getPostById(id);
        staticList = postService.getAllStaticPost();
        model.addAttribute("Static", staticList);
        model.addAttribute("post", post);
        return "static";
    }

    /**
     * This method will take in a req,post obj,principal and boolean which will
     * take in
     *
     * @param request
     * @param post
     * @param principal
     * @param isStatic
     * @return
     */
    @PostMapping("addPost")
    public String addPost(HttpServletRequest request, Post post, Principal principal, Boolean isStatic) {
        /* Principal is an interface
        A subset of subject that is represented by an account, role or other 
        unique identifier -principals are the unique keys we use in 
        access control lists.       
         */
        String Username = principal.getName();
        User user = userService.getUserByUsername(Username);

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByRoleName("ROLE_ADMIN"));
        roles.add(roleService.getRoleByRoleName("ROLE_USER"));

        //retrieves tags and split them with a comma
        String tags = request.getParameter("tags");
        String[] tagList = tags.split(",");
        List<Tags> tag = new ArrayList<>();

        LocalDateTime date = LocalDateTime.now();
        //setting up the post object with the given parameters
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

        if (isStatic != null) {
            post.setIsStatic(isStatic);
        } else {
            post.setIsStatic(false);
        }
        //validator for errors
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(post);
        if (violations.isEmpty()) {

            postService.addPost(post);
        }
        /* condition: if the post itself is marked static then go ahead
        and to the list */
        if (post.isIsStatic() == true) {
            staticList.add(postService.getPostById(post.getPostId()));
        }
        //tag retrieval in which it will be added 
        for (String newTag : tagList) {

            Tags setTag = new Tags(newTag);
            tagService.addTags(setTag);
            tag.add(tagService.getTagsById(setTag.getId()));
            tagService.createPostTag(post.getPostId(), setTag.getId());

        }

        return "redirect:/admin-post";

    }

}
