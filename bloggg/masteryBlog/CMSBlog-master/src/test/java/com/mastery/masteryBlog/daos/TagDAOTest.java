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
import static org.junit.Assert.fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shirley
 */
public class TagDAOTest {

    public TagDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addTags method, of class TagDao.
     */
//    @Test
    public void testAddTags() {
        System.out.println("addTags");
        Tags tags = null;
        TagDao instance = new TagDAOImpl();
        Tags expResult = null;
        Tags result = instance.addTags(tags);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTagsById method, of class TagDao.
     */
//    @Test
    public void testGetTagsById() {
        System.out.println("getTagsById");
        int tagId = 0;
        TagDao instance = new TagDAOImpl();
        Tags expResult = null;
        Tags result = instance.getTagsById(tagId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllTags method, of class TagDao.
     */
//    @Test
    public void testGetAllTags() {
        System.out.println("getAllTags");
        TagDao instance = new TagDAOImpl();
        List<Tags> expResult = null;
        List<Tags> result = instance.getAllTags();
        assertEquals(expResult, result);
    }

    /**
     * Test of updateTag method, of class TagDao.
     */
//    @Test
    public void testUpdateTag() {
        System.out.println("updateTag");
        Tags tag = null;
        TagDao instance = new TagDAOImpl();
        instance.updateTag(tag);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteTag method, of class TagDao.
     */
//    @Test
    public void testDeleteTag() {
        System.out.println("deleteTag");
        int tagId = 0;
        TagDao instance = new TagDAOImpl();
        instance.deleteTag(tagId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPostTag method, of class TagDao.
     */
//    @Test
    public void testCreatePostTag() {
        System.out.println("createPostTag");
        int postId = 0;
        int tagId = 0;
        TagDao instance = new TagDAOImpl();
        instance.createPostTag(postId, tagId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTagsByPostId method, of class TagDao.
     */
//    @Test
    public void testGetTagsByPostId() {
        System.out.println("getTagsByPostId");
        int postId = 0;
        TagDao instance = new TagDAOImpl();
        List<Tags> expResult = null;
        List<Tags> result = instance.getTagsByPostId(postId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deletePost method, of class TagDao.
     */
//    @Test
    public void testDeletePost() {
        System.out.println("deletePost");
        int postId = 0;
        TagDao instance = new TagDAOImpl();
        instance.deletePost(postId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Transactional
    public class TagDAOImpl implements TagDao {

        @Autowired
        JdbcTemplate jdbc;

        private final String addTag = "Insert Into Tags(tagId, tagName) Values (?,?)";
        private final String getTagById = "Select * From Tags Where tagId = ?";
        private final String getAllTags = "Select * From Tags";
        private final String updateTag = "Update Tags Set tagName = ? Where tagId = ?";
        private final String deleteTag = "Delete From Tags Where tagId = ?";
        private final String insertPostTags = "Insert Into PostTags(postId, tagId) Values (?,?)";
        private final String deleteFromPostTag = "Delete from PostTags Where postId = ?";

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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void createPostTag(int postId, int tagId) {
            jdbc.update(insertPostTags, postId, tagId);
        }

        @Override
        public void deletePost(int postId) {
            jdbc.update(deleteFromPostTag, postId);
        }

        public final class TagsJDBCMapper implements RowMapper<Tags> {

            @Override
            public Tags mapRow(ResultSet rs, int i) throws SQLException {

                Tags tag = new Tags();

                tag.setId(rs.getInt("tagId"));
                tag.setTagName(rs.getString("tagName"));

                return tag;

            }
        }
    }

}
