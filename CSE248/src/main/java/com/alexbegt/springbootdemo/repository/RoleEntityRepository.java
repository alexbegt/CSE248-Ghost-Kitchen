package com.alexbegt.springbootdemo.repository;

import com.alexbegt.springbootdemo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
}
