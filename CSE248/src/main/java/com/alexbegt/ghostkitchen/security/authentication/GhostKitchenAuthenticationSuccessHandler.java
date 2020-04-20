package com.alexbegt.ghostkitchen.security.authentication;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.security.login.LoggedInUser;
import com.alexbegt.ghostkitchen.security.user.ActiveUserStorage;
import com.alexbegt.ghostkitchen.service.DeviceService;
import com.alexbegt.ghostkitchen.util.Defaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component("myAuthenticationSuccessHandler")
public class GhostKitchenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Autowired
  private ActiveUserStorage activeUserStorage;

  @Autowired
  private DeviceService deviceService;

  /**
   * Once the user is logged in successfully, save the user's session and redirect them.
   *
   * @param request the http request in
   * @param response the http response in
   * @param authentication The user authentication to use
   * @throws IOException unable to read a file
   */
  @Override
  public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
    this.handle(request, response, authentication);

    final HttpSession session = request.getSession(false);

    if (session != null) {
      session.setMaxInactiveInterval(30 * 60);

      String username;

      if (authentication.getPrincipal() instanceof User) {
        username = ((User) authentication.getPrincipal()).getEmail();
      }
      else {
        username = authentication.getName();
      }

      LoggedInUser user = new LoggedInUser(username, this.activeUserStorage);
      session.setAttribute("user", user);
    }

    this.clearAuthenticationAttributes(request);

    this.verifyUserDeviceOrLocationAndSendEmail(authentication, request);
  }

  /**
   * Verifies the user's device or location is known, otherwise sends them an email.
   *
   * @param authentication the authentication passed
   * @param request the http request
   */
  private void verifyUserDeviceOrLocationAndSendEmail(Authentication authentication, HttpServletRequest request) {
    try {
      if (authentication.getPrincipal() instanceof User) {
        this.deviceService.verifyDevice(((User) authentication.getPrincipal()), request);
      }
    }
    catch (Exception e) {
      logger.error("An error occurred while verifying device or location", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Determines where to send the user after they login and redirects them there.
   *
   * @param request the http request in
   * @param response the http response in
   * @param authentication The user authentication to use
   * @throws IOException if a file cannot be opened
   */
  protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
    final String targetUrl = this.determineTargetUrl(authentication);

    if (response.isCommitted()) {
      this.logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }

    this.redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  /**
   * Determines where to send the user once they log in.
   *
   * @param authentication the authentication of the user
   * @return the target url to send them to when they log in. Either the console or the main home page TODO CHANGE
   */
  protected String determineTargetUrl(final Authentication authentication) {
    return "/home";
  }

  /**
   * Clears any login exception that has occurred once the user logins in.
   *
   * @param request the http request
   */
  protected void clearAuthenticationAttributes(final HttpServletRequest request) {
    final HttpSession session = request.getSession(false);

    if (session == null) {
      return;
    }

    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

  /**
   * Sets the new redirect strategy to use
   *
   * @param redirectStrategy the new redirect strategy
   */
  public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
    this.redirectStrategy = redirectStrategy;
  }

  /**
   * Gets the redirect strategy to be used.
   *
   * @return the redirect strategy
   */
  protected RedirectStrategy getRedirectStrategy() {
    return this.redirectStrategy;
  }
}
