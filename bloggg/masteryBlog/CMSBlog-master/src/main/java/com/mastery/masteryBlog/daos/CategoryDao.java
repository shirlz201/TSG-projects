/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.dtos.Category;
import java.util.List;

/**
 *
 * @author shirley
 */
public interface CategoryDao {

    Category addCategory(Category category);

    Category getCategoryById(int categoryId);

    List<Category> getAllCategories();

    void updateCategory(Category categoryId);

    void deleteCategory(int categoryId);

    List<Category> getCategoryByPostId(int postId);

}
