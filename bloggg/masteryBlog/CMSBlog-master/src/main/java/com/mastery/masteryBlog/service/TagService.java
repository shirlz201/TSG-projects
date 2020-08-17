/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.service;

import com.mastery.masteryBlog.dtos.Tags;
import java.util.List;

/**
 *
 * @author shirley
 */
public interface TagService {
    
    Tags addTags(Tags tags);
    Tags getTagsById(int tagId);
    List<Tags> getAllTags();
    void updateTag(Tags tag);
    void deleteTag(int tagId);
    void createPostTag(int postId, int tagId);
    void deletePost(int postId);
    List<Tags> getTagsByPostId(int postId);
    
}
