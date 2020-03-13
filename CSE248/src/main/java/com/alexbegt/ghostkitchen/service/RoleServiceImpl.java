package com.alexbegt.ghostkitchen.service;

import com.alexbegt.ghostkitchen.entity.RoleEntity;
import com.alexbegt.ghostkitchen.repository.RoleEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

  private final RoleEntityRepository repository;

  @Override
  public void createRoleEntity(RoleEntity roleEntity) {
    repository.save(roleEntity);
  }

  @Override
  public List<RoleEntity> getRoleEntities() {
    return repository.findAll();
  }

  @Override
  public RoleEntity getRoleById(Long id) {
    return repository.findById(id).orElse(null);
  }
}
