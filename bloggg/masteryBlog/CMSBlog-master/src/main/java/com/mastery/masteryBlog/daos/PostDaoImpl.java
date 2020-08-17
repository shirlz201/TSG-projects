/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.dtos.Category;
import com.mastery.masteryBlog.dtos.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PostDaoImpl implements PostDao {

    @Autowired
    JdbcTemplate jdbc;

    private final String createPost = "Insert into Post(`username`, title, postDate, categoryId, content, isApproved, isStatic, imagePath) Values (?,?,?,?,?,?,?,?)";
    private final String getAllPosts = "Select Post.postId, Post.`username`, Post.title, Post.postDate, cat.categoryId, cat.`description`, Post.content, group_concat(t.tagId), group_concat(t.tagName), Post.isApproved, Post.isStatic, Post.imagePath From Category As cat"
            + " Inner Join Post On Post.categoryId = cat.categoryId"
            + " Left Join PostTags pt on Post.postId = pt.postId"
            + " Left Join Tags t on pt.tagId = t.tagId"
            + " group by post.postId";
    private final String getPostById = "Select Post.postId, Post.`username`, Post.title, Post.postDate, cat.categoryId, cat.`description`, Post.content, group_concat(t.tagId), group_concat(t.tagName), Post.isApproved, Post.isStatic, Post.imagePath From Category As cat"
            + " Inner Join Post On Post.categoryId = cat.categoryId"
            + " Left Join PostTags pt on Post.postId = pt.postId"
            + " Left Join Tags t on pt.tagId = t.tagId"
            + " Where Post.postId = ?"
            + " group by post.postId";
    private final String getPostByCategoryId = "Select Post.postId, Post.`username`, Post.title, Post.postDate, cat.categoryId, cat.`description`, Post.content, group_concat(t.tagId), group_concat(t.tagName), Post.isApproved, Post.isStatic, Post.imagePath From Category As cat"
            + " Inner Join Post On Post.categoryId = cat.categoryId"
            + " Left Join PostTags pt on Post.postId = pt.postId"
            + " Left Join Tags t on pt.tagId = t.tagId"
            + " Where cat.categoryId = ?"
            + " group by post.postId";
    private final String getPostByTagName = "Select Post.postId, Post.`username`, Post.title, Post.postDate, cat.categoryId, cat.`description`, Post.content, group_concat(t.tagName), Post.isApproved, Post.isStatic, Post.imagePath From Category As cat "
            + "Join Post On Post.categoryId = cat.categoryId"
            + " left join PostTags pt on Post.postId = pt.postId"
            + " left join Tags t on pt.tagId = t.tagId"
            + " where t.tagName = ?"
            + " group by post.postId";
    private final String updatePost = "Update Post Set `username` = ?, title = ?, categoryId = ?, content = ?, imagePath = ? Where postId = ?";
    private final String updatePending = "Update Post Set `username` = ?, title = ?, postDate = ?, categoryId = ?, content = ?, isApproved = ?, imagePath = ? Where postId = ?";
    private final String deletePost = "Delete From Post Where postId = ?";
    private final String addImagePath = "Update Post Set imagePath = ? Where postId = ?";
    private final String getStaticPosts = "Select Post.postId, Post.`username`, Post.title, Post.postDate, cat.categoryId, cat.`description`, Post.content, group_concat(t.tagId), group_concat(t.tagName), Post.isApproved, Post.isStatic, Post.imagePath From Category As cat"
            + " Inner Join Post On Post.categoryId = cat.categoryId"
            + " Left Join PostTags pt on Post.postId = pt.postId"
            + " Left Join Tags t on pt.tagId = t.tagId"
            + " Where post.isStatic = true"
            + " group by post.postId";

    public PostDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Post addPost(Post post) {
        jdbc.update(createPost, post.getUsername(), post.getTitle(), post.getDateTime(), post.getCategory().getCategoryId(), post.getPosting(), post.isApproved(), post.isIsStatic(), post.getImagePath());
        post.setPostId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return post;

    }

    @Override
    public Post getPostById(int postId) {
        return jdbc.queryForObject(getPostById, new PostJDBCMapper(), postId);
    }

    @Override
    public List<Post> getAllPosts() {
        return jdbc.query(getAllPosts, new PostJDBCMapper());
    }

    @Override
    public void updatePost(Post post) {
        jdbc.update(updatePost, post.getUsername(), post.getTitle(), post.getCategory().getCategoryId(), post.getPosting(), post.getPostId(), post.getImagePath());
    }

    @Override
    public void deletePost(int postId) {
        jdbc.update(deletePost, postId);
    }

    @Override
    public List<Post> getPostsByCategoryId(int categoryId) {
        return jdbc.query(getPostByCategoryId, new PostJDBCMapper(), categoryId);
    }

    @Override
    public List<Post> getPostsByTagName(String tagName) {
        return jdbc.query(getPostByTagName, new PostJDBCMapper(), tagName);
    }

    @Override
    public List<Post> getPostsByUserName(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Post> getPostsByDisplayId(int displayId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatePending(Post post) {
        jdbc.update(updatePending, post.getUsername(), post.getTitle(), post.getDateTime(), post.getCategory().getCategoryId(), post.getPosting(), post.isApproved(), post.getImagePath(), post.getPostId());
    }

    @Override
    public void addImage(String imagePath, int id) {
        jdbc.update(addImagePath, imagePath, id);
    }

    @Override
    public List<Post> getAllStaticPost() {
        return jdbc.query(getStaticPosts, new PostJDBCMapper());
    }

    public static final class PostJDBCMapper implements RowMapper<Post> {

        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {

            Category category = new Category();

            category.setCategoryId(rs.getInt("categoryId"));
            category.setDescription(rs.getString("description"));

            Post post = new Post();

            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            post.setPostId(rs.getInt("postId"));
            post.setUsername(rs.getString("username"));
            post.setTitle(rs.getString("title"));
            post.setDateTime(LocalDateTime.parse(rs.getString("postDate"), format));
            post.setCategory(category);
            post.setPosting(rs.getString("content"));
            post.setTagList(rs.getString("group_concat(t.tagName)"));
            post.setApproved(rs.getBoolean("isApproved"));
            post.setIsStatic(rs.getBoolean("isStatic"));
            post.setImagePath(rs.getString("imagePath"));

            return post;

        }
    }

}
