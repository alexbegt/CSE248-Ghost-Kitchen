package com.alexbegt.ghostkitchen.security.google2fa;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class TwoFactorWebAuthenticationDetails extends WebAuthenticationDetails {

  private static final long serialVersionUID = 1L;

  private final String verificationCode;

  public TwoFactorWebAuthenticationDetails(HttpServletRequest request) {
    super(request);

    this.verificationCode = request.getParameter("code");
  }

  /**
   * Gets the two factor verification code
   *
   * @return the two factor verification code
   */
  public String getVerificationCode() {
    return this.verificationCode;
  }
}
