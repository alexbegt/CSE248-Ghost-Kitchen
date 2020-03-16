package com.alexbegt.ghostkitchen.repository;

import com.alexbegt.ghostkitchen.entity.RoleEntity;
import com.alexbegt.ghostkitchen.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(String name);
}
