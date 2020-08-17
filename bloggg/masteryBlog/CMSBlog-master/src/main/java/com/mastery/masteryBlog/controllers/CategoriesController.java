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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author shirley
 */
@Controller
public class CategoriesController {

    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    List<Post> staticList;

    /*The ConstraintViolation object holds information about the error; 
specifically, each one will hold the message of a validation error it found.*/
    Set<ConstraintViolation<Category>> violations = new HashSet<>();

    /**
     * This method will help retrieve the categories/posts/static posts in order
     * for them to be displayed in the categories page
     *
     * @param model
     * @return categories page
     */
    @GetMapping("categories")
    public String displayPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<Post> posts = postService.getAllPosts();
        staticList = postService.getAllStaticPost();
        model.addAttribute("Static", staticList);
        model.addAttribute("posts", posts);
        model.addAttribute("errors", violations);
        model.addAttribute("categories", categories);
        return "categories";
    }

    /**
     * This method will take in a request and category object in which will set
     * the object with the description user provides
     *
     * @param request
     * @param category
     * @return categories page
     */
    @PostMapping("addCategory")
    public String addCategory(HttpServletRequest request, Category category) {
        if (category != null) {

            category.setDescription(request.getParameter("description"));
        }

        if (violations.isEmpty()) {
            categoryService.addCategory(category);
        }
        return "redirect:/categories";
    }

    /**
     * This method will take in a request which will retrieve the category id in
     * order for it to be deleted
     *
     * @param request
     * @return categories page
     */
    @GetMapping("deleteCategory")
    public String deletePost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }

}
