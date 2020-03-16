package com.alexbegt.ghostkitchen.service.user;

import com.alexbegt.ghostkitchen.model.User;

import java.util.List;

public interface UserService {

  void createUser(User user);

  User getUserById(Long id);

  void deleteUserById(Long id);

  User getUserByEmail(String email);

  User getUserByUsername(String username);

  User getUserByUsernameOrEmail(String usernameOrEmail);

  List<User> getAllUsers();
}
