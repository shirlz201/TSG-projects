/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.dtos.Role;
import java.util.List;

/**
 *
 * @author shirley
 */
public interface RoleDao {

    Role createRole(Role role);

    Role getRoleById(int role);

    Role getRoleByRoleName(String role);

    List<Role> getAllRoles();

    void deleteRole(int id);

    void updateRole(Role role);

}
