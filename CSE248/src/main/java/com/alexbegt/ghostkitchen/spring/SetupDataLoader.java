package com.alexbegt.ghostkitchen.spring;

import com.alexbegt.ghostkitchen.persistence.dao.role.PrivilegeRepository;
import com.alexbegt.ghostkitchen.persistence.dao.role.RoleRepository;
import com.alexbegt.ghostkitchen.persistence.dao.user.UserRepository;
import com.alexbegt.ghostkitchen.persistence.model.role.Privilege;
import com.alexbegt.ghostkitchen.persistence.model.role.Role;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.util.Defaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PrivilegeRepository privilegeRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (alreadySetup) {
      return;
    }

    // == create initial privileges
    final Privilege readPrivilege = this.createPrivilegeIfNotFound(Defaults.READ_PRIVILEGE);
    final Privilege writePrivilege = this.createPrivilegeIfNotFound(Defaults.WRITE_PRIVILEGE);
    final Privilege passwordPrivilege = this.createPrivilegeIfNotFound(Defaults.CHANGE_PASSWORD_PRIVILEGE);

    // == create initial roles
    final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
    final List<Privilege> userPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, passwordPrivilege));
    final Role adminRole = this.createRoleIfNotFound(Defaults.ADMIN_ROLE, adminPrivileges);
    this.createRoleIfNotFound(Defaults.USER_ROLE, userPrivileges);

    // == create initial user
    this.createUserIfNotFound(Defaults.ADMIN_FIRST_NAME, Defaults.ADMIN_LAST_NAME, Defaults.ADMIN_EMAIL, Defaults.ADMIN_PASSWORD, new ArrayList<>(Collections.singletonList(adminRole)));

    alreadySetup = true;
  }

  @Transactional
  Privilege createPrivilegeIfNotFound(final String name) {
    Privilege privilege = this.privilegeRepository.findByName(name);

    if (privilege == null) {
      privilege = new Privilege(name);
      privilege = this.privilegeRepository.save(privilege);
    }

    return privilege;
  }

  @Transactional
  Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
    Role role = this.roleRepository.findByName(name);

    if (role == null) {
      role = new Role(name);
    }

    role.setPrivileges(privileges);
    role = this.roleRepository.save(role);

    return role;
  }

  @Transactional
  void createUserIfNotFound(final String firstName, final String lastName, final String email, final String password, final Collection<Role> roles) {
    User user = this.userRepository.findByEmail(email);

    if (user == null) {
      user = new User();
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setEmail(email);
      user.setPassword(this.passwordEncoder.encode(password));
      user.setEnabled(true);
    }

    user.setRoles(roles);

    this.userRepository.save(user);
  }
}
