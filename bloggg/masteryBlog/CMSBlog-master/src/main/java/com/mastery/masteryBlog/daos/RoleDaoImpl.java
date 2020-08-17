/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.daos;

import com.mastery.masteryBlog.dtos.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    JdbcTemplate jdbc;

    private final String createRole = "Insert Into role(role) Values(?)";
    private final String getRoleById = "Select * From role Where id = ?";
    private final String getAllRoles = "Select * From role";
    private final String getRoleByName = "Select * From role Where role = ?";
    private final String updateRole = "Update role Set role = ? Where id = ?";
    private final String deleteRole = "Delete From role Where id = ?";
    private final String deleteRoleIdFromUserRole = "Delete From user_role WHERE role_id = ?";

    public RoleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Role createRole(Role role) {
        jdbc.update(createRole, role.getRole());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        role.setId(newId);
        return role;
    }

    @Override
    public Role getRoleById(int id) {
        return jdbc.queryForObject(getRoleById, new RoleMapper(), id);
    }

    @Override
    public Role getRoleByRoleName(String role) {
        return jdbc.queryForObject(getRoleByName, new RoleMapper(), role);
    }

    @Override
    public List<Role> getAllRoles() {
        return jdbc.query(getAllRoles, new RoleMapper());
    }

    @Override
    public void deleteRole(int id) {
        jdbc.update(deleteRoleIdFromUserRole);
        jdbc.update(deleteRole, id);
    }

    @Override
    public void updateRole(Role role) {
        jdbc.update(updateRole, role.getRole(), role.getId());
    }

    public static final class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setRole(rs.getString("role"));
            return role;
        }
    }

}
