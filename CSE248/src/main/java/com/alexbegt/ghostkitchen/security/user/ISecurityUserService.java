package com.alexbegt.ghostkitchen.security.user;

public interface ISecurityUserService {

  /**
   * Checks the given token to see if it's valid or not.
   *
   * @param token the password token string
   * @return if the token is valid, returns a null string, if expired returns expired, or if invalid, returns invalidToken.
   */
  String validatePasswordResetToken(String token);
}
