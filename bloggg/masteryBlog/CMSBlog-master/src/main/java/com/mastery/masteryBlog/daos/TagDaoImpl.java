/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.dtos.Tags;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TagDaoImpl implements TagDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    private final String addTag = "Insert Into Tags(tagId, tagName) Values (?,?)";
    private final String getTagById = "Select * From Tags Where tagId = ?";
    private final String getAllTags = "Select * From Tags";
    private final String updateTag = "Update Tags Set tagName = ? Where tagId = ?";
    private final String deleteTag = "Delete From Tags Where tagId = ?";
    private final String insertPostTags = "Insert Into PostTags(postId, tagId) Values (?,?)";
    private final String deleteFromPostTag = "Delete from PostTags Where postId = ?";
    
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Tags addTags(Tags tags) {
        jdbc.update(addTag, tags.getId(), tags.getTagName());
        tags.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return tags;
    }

    @Override
    public Tags getTagsById(int tagId) {
        return jdbc.queryForObject(getTagById, new TagsJDBCMapper(), tagId);
    }

    @Override
    public List<Tags> getAllTags() {
        return jdbc.query(getAllTags, new TagsJDBCMapper());
    }

    @Override
    public void updateTag(Tags tag) {
        jdbc.update(updateTag, tag.getTagName(), tag.getId());
    }

    @Override
    public void deleteTag(int tagId) {
        jdbc.update(deleteTag);
    }

    @Override
    public List<Tags> getTagsByPostId(int postId) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void createPostTag(int postId, int tagId) {
        jdbc.update(insertPostTags, postId, tagId);
    }
    
    @Override
    public void deletePost(int postId) {
        jdbc.update(deleteFromPostTag, postId);
    }
    
        public static final class TagsJDBCMapper implements RowMapper<Tags> {

        @Override
        public Tags mapRow(ResultSet rs, int i) throws SQLException {
            
            Tags tag = new Tags();
            
            tag.setId(rs.getInt("tagId"));
            tag.setTagName(rs.getString("tagName"));
            
            return tag;
            
        }
    }
    
}
