package com.alexbegt.ghostkitchen.transformer;

import com.alexbegt.ghostkitchen.entity.UserEntity;
import com.alexbegt.ghostkitchen.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
  UserEntity mapUserToUserEntity(User user);

  User mapUserEntityToUser(UserEntity userEntity);

  List<User> mapUserEntityListToUserList(List<UserEntity> userEntities);
}
