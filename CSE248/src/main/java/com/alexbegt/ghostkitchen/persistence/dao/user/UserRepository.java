package com.alexbegt.ghostkitchen.persistence.dao.user;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  @Override
  void delete(User user);
}
