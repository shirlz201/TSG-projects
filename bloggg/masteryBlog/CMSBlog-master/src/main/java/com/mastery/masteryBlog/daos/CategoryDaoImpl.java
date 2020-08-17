/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.dtos.Category;
import com.mastery.masteryBlog.dtos.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    JdbcTemplate jdbc;

    private final String createCategory = "Insert Into Category(`description`) Values (?)";
    private final String getCategoryById = "Select * From Category Where categoryId = ?";
    private final String getAllCategories = "Select * From Category";
    private final String updateCategory = "Update Category Set `description` = ? Where categoryId = ?";
    private final String deleteCategory = "Delete From Category WHERE categoryId = ?";

    public CategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Category addCategory(Category category) {
        jdbc.update(createCategory, category.getDescription());
        category.setCategoryId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return category;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return jdbc.queryForObject(getCategoryById, new CategoryJDBCMapper(), categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return jdbc.query(getAllCategories, new CategoryJDBCMapper());
    }

    @Override
    public void updateCategory(Category category) {
        jdbc.update(updateCategory, category.getDescription(), category.getCategoryId());
    }

    @Override
    public void deleteCategory(int categoryId) {
        jdbc.update(deleteCategory, categoryId);
    }

    @Override
    public List<Category> getCategoryByPostId(int postId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static final class CategoryJDBCMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {

            Category category = new Category();

            category.setCategoryId(rs.getInt("categoryId"));
            category.setDescription(rs.getString("description"));

            return category;

        }
    }

}
