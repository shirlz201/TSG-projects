/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.dtos.Tags;
import java.util.List;

/**
 *
 * @author shirley
 */
public interface TagDao {

    Tags addTags(Tags tags);

    Tags getTagsById(int tagId);

    List<Tags> getAllTags();

    void updateTag(Tags tag);

    void deleteTag(int tagId);

    void createPostTag(int postId, int tagId);

    List<Tags> getTagsByPostId(int postId);

    void deletePost(int postId);

}
