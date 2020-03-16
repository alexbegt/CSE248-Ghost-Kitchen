package com.alexbegt.ghostkitchen.service.role;

import com.alexbegt.ghostkitchen.model.Role;
import com.alexbegt.ghostkitchen.repository.RoleEntityRepository;
import com.alexbegt.ghostkitchen.transformer.RoleMapper;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

  private final RoleEntityRepository repository;
  private final RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

  @Override
  public void createRole(Role role) {
    repository.save(mapper.mapRoleToRoleEntity(role));
  }

  @Override
  public Role getRoleById(Long id) {
    return mapper.mapRoleEntityToRole(repository.findById(id).orElse(null));
  }

  @Override
  public void deleteRoleById(Long id) {
    repository.deleteById(id);
  }

  @Override
  public Role getRoleByName(String name) {
    return mapper.mapRoleEntityToRole(repository.findByName(name).orElse(null));
  }

  @Override
  public List<Role> getAllRoles() {
    return mapper.mapRoleEntityListToRoleList(repository.findAll());
  }
}
