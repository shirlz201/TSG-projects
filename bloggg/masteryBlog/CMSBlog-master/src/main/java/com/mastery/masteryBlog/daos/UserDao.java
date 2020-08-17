/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.dtos.Role;
import com.mastery.masteryBlog.dtos.User;
import java.util.List;
import java.util.Set;

/**
 *
 * @author shirley
 */
public interface UserDao {

    User createUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(int id);

}
