package com.alexbegt.ghostkitchen.persistence.dao.user;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find a user by their email.
   *
   * @param email the email to use to find the user
   * @return the user if found
   */
  User findByEmail(String email);

  /**
   * Deletes a given user.
   *
   * @param user must not be {@literal null}.
   * @throws IllegalArgumentException in case the given entity is {@literal null}.
   */
  @Override
  void delete(User user);
}
