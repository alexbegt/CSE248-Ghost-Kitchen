package com.alexbegt.ghostkitchen.model;

import com.alexbegt.ghostkitchen.entity.RoleEntity;
import com.alexbegt.ghostkitchen.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {

  private Long id;
  private UUID uuid;
  private String username;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String streetAddress;
  private String city;
  private String state;
  private String zipCode;
  private Set<RoleEntity> roles;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 2;

    return prime * result + (email == null ? 0 : email.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof UserEntity) {

      UserEntity userEntity = (UserEntity) obj;

      return this.username.equals(userEntity.getUsername()) &&
        this.email.equals(userEntity.getEmail()) &&
        this.password.equals(userEntity.getPassword()) &&
        this.firstName.equals(userEntity.getFirstName()) &&
        this.lastName.equals(userEntity.getLastName()) &&
        this.streetAddress.equals(userEntity.getStreetAddress()) &&
        this.city.equals(userEntity.getCity()) &&
        this.state.equals(userEntity.getState()) &&
        this.zipCode.equals(userEntity.getZipCode());
    }

    return false;
  }
}
