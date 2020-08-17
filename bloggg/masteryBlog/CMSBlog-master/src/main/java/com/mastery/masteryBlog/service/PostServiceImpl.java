/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.service;

import com.mastery.masteryBlog.dtos.Post;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mastery.masteryBlog.daos.PostDao;

@Service
public class PostServiceImpl implements PostService {
    
    @Autowired
    PostDao pDao;

    @Override
    public Post addPost(Post post) {
        return pDao.addPost(post);
    }

    @Override
    public Post getPostById(int postId) {
        return pDao.getPostById(postId);
    }

    @Override
    public List<Post> getAllPosts() {
        return pDao.getAllPosts();
    }

    @Override
    public void updatePost(Post post) {
        pDao.updatePost(post);
    }

    @Override
    public void deletePost(int postId) {
        pDao.deletePost(postId);
    }

    @Override
    public List<Post> getPostsByCategoryId(int catagoryId) {
        return pDao.getPostsByCategoryId(catagoryId);
    }

    @Override
    public List<Post> getPostsByTagName(String tagName) {
        return pDao.getPostsByTagName(tagName);
    }

    @Override
    public List<Post> getPostsByUserId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Post> getPostsByDisplayId(int displayId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatePending(Post post) {
        pDao.updatePending(post);
    }

    @Override
    public void addImage(String imagePath, int id) {
        pDao.addImage(imagePath, id);
    }

    @Override
    public List<Post> getAllStaticPost() {
        return pDao.getAllStaticPost();
    }
    
}
