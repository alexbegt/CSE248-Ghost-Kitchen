package com.alexbegt.ghostkitchen.persistence.dao.role;

import com.alexbegt.ghostkitchen.persistence.model.role.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

  /**
   * Find a privilege by its name.
   *
   * @param name the name to use to find the privilege
   * @return the privilege if found
   */
  Privilege findByName(String name);

  /**
   * Deletes a given privilege.
   *
   * @param privilege must not be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  void delete(Privilege privilege);
}
