package com.alexbegt.ghostkitchen.security.authentication;

import com.alexbegt.ghostkitchen.security.login.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private LoginAttemptService loginAttemptService;

  /**
   * When the authentication fails, adds a login attempt to the users IP.
   *
   * @param authenticationFailureBadCredentialsEvent the failure event
   */
  @Override
  public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
    final String xfHeader = this.request.getHeader("X-Forwarded-For");

    if (xfHeader == null) {
      this.loginAttemptService.loginFailed(this.request.getRemoteAddr());
    }
    else {
      this.loginAttemptService.loginFailed(xfHeader.split(",")[0]);
    }
  }
}
