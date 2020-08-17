/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.service;

import com.mastery.masteryBlog.dtos.User;
import java.util.List;

/**
 *
 * @author shirley
 */
public interface UserService {

    User createUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(int id);

}
