package com.alexbegt.springbootdemo.service;

import com.alexbegt.springbootdemo.model.User;

import java.util.List;

public interface UserService {

  void createUser(User user);

  User getUserById(Long id);

  void deleteUserById(Long id);

  User getUserByEmail(String email);

  User getUserByUsername(String username);

  List<User> getAllUsers();
}
