/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.service;

import com.mastery.masteryBlog.dtos.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mastery.masteryBlog.daos.CategoryDao;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    CategoryDao categoryDao;

    @Override
    public Category addCategory(Category catagory) {
        return categoryDao.addCategory(catagory);
    }

    @Override
    public Category getCategoryById(int catagoryId) {
        return categoryDao.getCategoryById(catagoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public void updateCategory(Category categoryId) {
        categoryDao.updateCategory(categoryId);
    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryDao.deleteCategory(categoryId);
    }

    @Override
    public List<Category> getCategoryByPostId(int postId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
