package com.alexbegt.ghostkitchen.service.user;

import com.alexbegt.ghostkitchen.model.User;
import com.alexbegt.ghostkitchen.repository.UserEntityRepository;
import com.alexbegt.ghostkitchen.transformer.UserMapper;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

  private final UserEntityRepository repository;
  private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

  @Override
  public void createUser(User user) {
    repository.save(mapper.mapUserToUserEntity(user));
  }

  @Override
  public User getUserById(Long id) {
    return mapper.mapUserEntityToUser(repository.findById(id).orElse(null));
  }

  @Override
  public void deleteUserById(Long id) {
    repository.deleteById(id);
  }

  @Override
  public List<User> getAllUsers() {
    return mapper.mapUserEntityListToUserList(repository.findAll());
  }

  @Override
  public User getUserByEmail(String email) {
    return mapper.mapUserEntityToUser(repository.findByEmail(email).orElse(null));
  }

  @Override
  public User getUserByUsername(String username) {
    return mapper.mapUserEntityToUser(repository.findByUsername(username).orElse(null));
  }

  @Override
  public User getUserByUsernameOrEmail(String usernameOrEmail) {
    User user = mapper.mapUserEntityToUser(repository.findByUsername(usernameOrEmail).orElse(null));

    if (user == null) {
      user = mapper.mapUserEntityToUser(repository.findByEmail(usernameOrEmail).orElse(null));
    }

    return user;
  }
}
