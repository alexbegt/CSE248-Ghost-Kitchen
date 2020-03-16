package com.alexbegt.ghostkitchen.service.role;

import com.alexbegt.ghostkitchen.model.Role;

import java.util.List;

public interface RoleService {
  void createRole(Role role);

  Role getRoleById(Long id);

  void deleteRoleById(Long id);

  Role getRoleByName(String name);

  List<Role> getAllRoles();
}
