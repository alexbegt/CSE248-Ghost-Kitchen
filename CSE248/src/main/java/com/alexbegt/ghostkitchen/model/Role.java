package com.alexbegt.ghostkitchen.model;

import com.alexbegt.ghostkitchen.entity.RoleEntity;
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
public class Role {
  private Long id;
  private UUID uuid;
  private String name;
  private String description;
  private boolean isAssignable;

  private Set<User> users;
}
