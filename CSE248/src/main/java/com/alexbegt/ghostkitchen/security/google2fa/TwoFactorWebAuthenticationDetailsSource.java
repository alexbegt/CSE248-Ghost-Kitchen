package com.alexbegt.ghostkitchen.security.google2fa;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TwoFactorWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

  /**
   * Builds the authentication details
   *
   * @param context the http servlet request
   * @return the two factor authentication details
   */
  @Override
  public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
    return new TwoFactorWebAuthenticationDetails(context);
  }
}
