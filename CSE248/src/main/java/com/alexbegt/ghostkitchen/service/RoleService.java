package com.alexbegt.ghostkitchen.service;

import com.alexbegt.ghostkitchen.entity.RoleEntity;

import java.util.List;

public interface RoleService {

  void createRoleEntity(RoleEntity roleEntity);

  List<RoleEntity> getRoleEntities();

  RoleEntity getRoleById(Long id);
}
