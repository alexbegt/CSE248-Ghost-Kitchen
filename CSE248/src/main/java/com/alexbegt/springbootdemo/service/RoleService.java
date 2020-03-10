package com.alexbegt.springbootdemo.service;

import com.alexbegt.springbootdemo.entity.RoleEntity;

import java.util.List;

public interface RoleService {

  void createRoleEntity(RoleEntity roleEntity);

  List<RoleEntity> getRoleEntities();

  RoleEntity getRoleById(Long id);
}
