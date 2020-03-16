package com.alexbegt.ghostkitchen.transformer;

import com.alexbegt.ghostkitchen.entity.RoleEntity;
import com.alexbegt.ghostkitchen.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

  RoleEntity mapRoleToRoleEntity(Role role);

  Role mapRoleEntityToRole(RoleEntity roleEntity);

  List<Role> mapRoleEntityListToRoleList(List<RoleEntity> roleEntities);
}
