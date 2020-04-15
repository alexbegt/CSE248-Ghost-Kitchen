package com.alexbegt.ghostkitchen.security.login;

import com.alexbegt.ghostkitchen.persistence.dao.user.UserRepository;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class GhostKitchenRememberMeServices extends PersistentTokenBasedRememberMeServices {

  @Autowired
  private UserRepository userRepository;

  private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
  private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
  private final PersistentTokenRepository tokenRepository;
  private final String key;

  public GhostKitchenRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
    super(key, userDetailsService, tokenRepository);

    this.tokenRepository = tokenRepository;
    this.key = key;
  }

  /**
   * Creates a cookie for the user to bve used with remember me.
   *
   * @param request the request
   * @param response the response
   * @param successfulAuthentication the successful authentication
   */
  @Override
  protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
    String username = ((User) successfulAuthentication.getPrincipal()).getEmail();

    logger.debug("Creating new persistent login for user " + username);

    PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(username, this.generateSeriesData(), this.generateTokenData(), new Date());

    try {
      this.tokenRepository.createNewToken(persistentToken);
      this.addCookie(persistentToken, request, response);
    }
    catch (Exception e) {
      logger.error("Failed to save persistent token ", e);
    }
  }

  /**
   * Creates a new authentication from the remember me.
   *
   * @param request the http request
   * @param user the user details to use
   * @return the new authentication
   */
  @Override
  protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
    User userRepositoryByEmail = this.userRepository.findByEmail(user.getUsername());

    RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(this.key, userRepositoryByEmail, this.authoritiesMapper.mapAuthorities(user.getAuthorities()));

    auth.setDetails(this.authenticationDetailsSource.buildDetails(request));

    return auth;
  }

  /**
   * Adds a remember me cookie to the request
   *
   * @param token the token
   * @param request the request
   * @param response the response back
   */
  private void addCookie(PersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
    this.setCookie(new String[] { token.getSeries(), token.getTokenValue() }, this.getTokenValiditySeconds(), request, response);
  }
}
