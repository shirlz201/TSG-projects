/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.daos.RoleDaoImpl.RoleMapper;
import com.mastery.masteryBlog.dtos.Role;
import com.mastery.masteryBlog.dtos.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    private final String createUser = "Insert Into user(`username`, `password`, `enabled`) Values (?,?,?)";
    private final String setUserRole = "Insert Into user_role(`user_id`, `role_id`) Values (?,?)";
    private final String getUserById = "Select * From user Where `id` = ?";
    private final String getUserByUsername = "Select * From user Where username = ?";
    private final String getAllUsers = "Select * From user";
    private final String rolesForUser = "SELECT r.* FROM `user_role` ur JOIN role r ON ur.role_id = r.id WHERE ur.user_id = ?";
    private final String updateUser = "Update user Set `username` = ?, `password` = ?, `enabled` = ? Where `id` = ?";
    private final String deleteUser = "Delete From user Where `id` = ?";
    private final String deleteUserRole = "Delete From user_role Where user_id = ?";
    
    public UserDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbc = jdbcTemplate;
    }
    
    private Set<Role> getUserRole(int id){
        Set<Role> roles = new HashSet(jdbc.query(rolesForUser, new RoleMapper(), id));
        return roles;
    }

    @Override
    public User createUser(User user) {
        jdbc.update(createUser, user.getUsername(), user.getPassword(), user.isEnabled());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setId(newId);
        
        for (Role role : user.getRoles()){
            jdbc.update(setUserRole, user.getId(), role.getId());
        }
        
        return user;
    }

    @Override
    public User getUserById(int id) {
        User user = jdbc.queryForObject(getUserById, new UserMapper(), id);
        user.setRoles(getUserRole(user.getId()));
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
      User user = jdbc.queryForObject(getUserByUsername, new UserMapper(), username);
      user.setRoles(getUserRole(user.getId()));
      return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = jdbc.query(getAllUsers, new UserMapper());
        
        for (User user : users) {
            user.setRoles(getRolesForUser(user.getId()));
        }
        return users;
    }

    @Override
    public void updateUser(User user) {
        jdbc.update(updateUser, user.getUsername(), user.getPassword(), user.isEnabled(), user.getId());
        jdbc.update(deleteUserRole, user.getId());
        
        for(Role role : user.getRoles()){
            jdbc.update(setUserRole, user.getId(), role.getId());
        }
    }

    @Override
    public void deleteUser(int id) {
        jdbc.update(deleteUserRole, id);
        jdbc.update(deleteUser, id);
    }
    
    private Set<Role> getRolesForUser(int id) throws DataAccessException {
        Set<Role> roles = new HashSet(jdbc.query(rolesForUser, new RoleMapper(), id));
        return roles;
    }
    
        public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            return user;
        }
    }
    
}
