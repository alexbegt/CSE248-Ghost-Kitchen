package com.alexbegt.ghostkitchen.persistence.dao.role;

import com.alexbegt.ghostkitchen.persistence.model.role.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

  Privilege findByName(String name);

  @Override
  void delete(Privilege privilege);
}
