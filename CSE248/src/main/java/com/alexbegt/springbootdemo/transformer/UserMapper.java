package com.alexbegt.springbootdemo.transformer;

import com.alexbegt.springbootdemo.entity.UserEntity;
import com.alexbegt.springbootdemo.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
  UserEntity mapUserToUserEntity(User user);

  User mapUserEntityToUser(UserEntity userEntity);

  List<User> mapUserEntityListToUserList(List<UserEntity> userEntities);
}
