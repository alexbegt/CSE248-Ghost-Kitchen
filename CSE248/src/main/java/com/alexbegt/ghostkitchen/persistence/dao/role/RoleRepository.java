package com.alexbegt.ghostkitchen.persistence.dao.role;

import com.alexbegt.ghostkitchen.persistence.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  /**
   * Find a role by its name.
   *
   * @param name the name to use to find the role
   * @return the role if found
   */
  Role findByName(String name);

  /**
   * Deletes a given role.
   *
   * @param role must not be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  void delete(Role role);
}
