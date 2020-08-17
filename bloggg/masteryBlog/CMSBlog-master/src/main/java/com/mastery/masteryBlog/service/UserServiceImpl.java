/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.service;

import com.mastery.masteryBlog.dtos.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.mastery.masteryBlog.daos.UserDao;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserDao uDao;

    @Override
    public User createUser(User user) {
        return uDao.createUser(user);
    }

    @Override
    public User getUserById(int id) {
        try {
           return uDao.getUserById(id);
        } catch (DataAccessException ex) {
           return null; 
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
           return uDao.getUserByUsername(username);
        } catch (DataAccessException ex) {
           return null; 
        }
    }

    @Override
    public List<User> getAllUsers() {
        return uDao.getAllUsers();       
    }

    @Override
    public void updateUser(User user) {
        uDao.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        uDao.deleteUser(id);
    }
    
}
