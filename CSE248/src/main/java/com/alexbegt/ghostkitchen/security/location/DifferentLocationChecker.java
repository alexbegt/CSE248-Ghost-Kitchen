package com.alexbegt.ghostkitchen.security.location;

import com.alexbegt.ghostkitchen.persistence.model.device.NewLocationToken;
import com.alexbegt.ghostkitchen.service.IUserService;
import com.alexbegt.ghostkitchen.web.error.UnusualLocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class DifferentLocationChecker implements UserDetailsChecker {

  private final IUserService userService;

  private final HttpServletRequest request;

  private final ApplicationEventPublisher eventPublisher;

  @Autowired
  public DifferentLocationChecker(IUserService userService, HttpServletRequest request, ApplicationEventPublisher eventPublisher) {
    this.userService = userService;
    this.request = request;
    this.eventPublisher = eventPublisher;
  }

  /**
   * Checks if a new location token exists for the given user details and ip.
   *
   * @param toCheck the user details to check
   */
  @Override
  public void check(UserDetails toCheck) {
    final String ip = this.getClientIP();
    final NewLocationToken token = this.userService.isNewLoginLocation(toCheck.getUsername(), ip);

    if (token != null) {
      final String appUrl = "http://" + this.request.getServerName() + ":" + this.request.getServerPort() + this.request.getContextPath();

      this.eventPublisher.publishEvent(new OnDifferentLocationLoginEvent(this.request.getLocale(), toCheck.getUsername(), ip, token, appUrl));

      throw new UnusualLocationException("unusual location");
    }
  }

  /**
   * Gets the client IP
   *
   * @return the client ip
   */
  private String getClientIP() {
    final String xfHeader = this.request.getHeader("X-Forwarded-For");

    if (xfHeader == null) {
      return this.request.getRemoteAddr();
    }

    return xfHeader.split(",")[0];

    // return "128.101.101.101"; // for testing United States
    // return "41.238.0.198"; // for testing Egypt
  }
}