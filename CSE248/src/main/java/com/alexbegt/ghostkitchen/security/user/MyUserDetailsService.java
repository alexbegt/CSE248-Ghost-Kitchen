package com.alexbegt.ghostkitchen.security.user;

import com.alexbegt.ghostkitchen.persistence.dao.user.UserRepository;
import com.alexbegt.ghostkitchen.persistence.model.role.Privilege;
import com.alexbegt.ghostkitchen.persistence.model.role.Role;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.security.login.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private LoginAttemptService loginAttemptService;

  @Autowired
  private HttpServletRequest request;

  public MyUserDetailsService() {
    super();
  }

  /**
   * Loads the user by the given details.
   *
   * @param email the email to check by
   * @return the user details to use
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    final String ip = this.getClientIP();

    if (this.loginAttemptService.isBlocked(ip)) {
      throw new RuntimeException("blocked");
    }

    try {
      final User user = this.userRepository.findByEmail(email);

      if (user == null) {
        throw new UsernameNotFoundException("No user found with username: " + email);
      }

      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, this.getAuthorities(user.getRoles()));
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets the authorities the account should have from a collection of roles.
   *
   * @param roles the collection of roles
   * @return the list of authorities
   */
  private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
    return this.getGrantedAuthorities(this.getPrivileges(roles));
  }

  /**
   * Gets the privileges the account should have from the set of roles.
   *
   * @param roles the collection of roles
   * @return the list of privileges
   */
  private List<String> getPrivileges(final Collection<Role> roles) {
    final List<String> privileges = new ArrayList<>();
    final List<Privilege> collection = new ArrayList<>();

    for (final Role role : roles) {
      collection.addAll(role.getPrivileges());
    }

    for (final Privilege item : collection) {
      privileges.add(item.getName());
    }

    return privileges;
  }

  /**
   * Gets the list of authorities the account should have
   *
   * @param privileges the list of privileges
   * @return the list of granted authorities
   */
  private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
    final List<GrantedAuthority> authorities = new ArrayList<>();

    for (final String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }

    return authorities;
  }

  /**
   * Get's the client ip from the header.
   *
   * @return the clients ip
   */
  private String getClientIP() {
    final String xfHeader = this.request.getHeader("X-Forwarded-For");

    if (xfHeader == null) {
      return this.request.getRemoteAddr();
    }

    return xfHeader.split(",")[0];
  }
}
