package com.alexbegt.ghostkitchen.service.user;

import com.alexbegt.ghostkitchen.entity.RoleEntity;
import com.alexbegt.ghostkitchen.model.Role;
import com.alexbegt.ghostkitchen.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserServiceImpl userService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userService.getUserByUsernameOrEmail(email);
    List<String> roleNames = new ArrayList<>();
    List<GrantedAuthority> authorityList = new ArrayList<>();

    try {
      if (user == null) {
        log.info("User with this email or username not found: " + email);
        throw new UsernameNotFoundException("User " + email + " was not found in the database");
      }
    } catch (UsernameNotFoundException e) {
      e.getMessage();
    }

    if (user == null) {
      throw new UsernameNotFoundException("User " + email + " was not found in the database");
    } else {
      log.info("Found user: " + email);

      for (Role roleEntity : user.getRoles()) {
        roleNames.add(roleEntity.getName());
      }

      if (roleNames.size() > 0) {
        for (String role : roleNames) {
          GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
          authorityList.add(grantedAuthority);
        }
      } else {
        authorityList.add(new SimpleGrantedAuthority("USER"));
      }

      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorityList);
    }
  }
}