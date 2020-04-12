package com.alexbegt.ghostkitchen.security.authentication;

import com.alexbegt.ghostkitchen.security.login.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private LoginAttemptService loginAttemptService;

  /**
   * On the user logging in successfully, clears the login attempts on the ip.
   *
   * @param authenticationSuccessEvent the login success event
   */
  @Override
  public void onApplicationEvent(final AuthenticationSuccessEvent authenticationSuccessEvent) {
    final String xfHeader = this.request.getHeader("X-Forwarded-For");

    if (xfHeader == null) {
      this.loginAttemptService.loginSucceeded(this.request.getRemoteAddr());
    }
    else {
      this.loginAttemptService.loginSucceeded(xfHeader.split(",")[0]);
    }
  }
}
