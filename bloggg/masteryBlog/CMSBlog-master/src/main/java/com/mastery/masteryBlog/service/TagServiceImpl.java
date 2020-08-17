/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.service;

import com.mastery.masteryBlog.dtos.Tags;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mastery.masteryBlog.daos.TagDao;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Override
    public Tags addTags(Tags tags) {
        return tagDao.addTags(tags);
    }

    @Override
    public Tags getTagsById(int tagId) {
        return tagDao.getTagsById(tagId);
    }

    @Override
    public List<Tags> getAllTags() {
        return tagDao.getAllTags();
    }

    @Override
    public void updateTag(Tags tag) {
        tagDao.updateTag(tag);
    }

    @Override
    public void deleteTag(int tagId) {
        tagDao.deleteTag(tagId);
    }

    @Override
    public List<Tags> getTagsByPostId(int postId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createPostTag(int postId, int tagId) {
        tagDao.createPostTag(postId, tagId);
    }

    @Override
    public void deletePost(int postId) {
        tagDao.deletePost(postId);
    }

}
