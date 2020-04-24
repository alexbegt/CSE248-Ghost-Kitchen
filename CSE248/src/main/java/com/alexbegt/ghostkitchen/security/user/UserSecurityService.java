package com.alexbegt.ghostkitchen.security.user;

import com.alexbegt.ghostkitchen.persistence.dao.user.token.PasswordResetTokenRepository;
import com.alexbegt.ghostkitchen.persistence.model.user.token.PasswordResetToken;
import com.alexbegt.ghostkitchen.util.Defaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;

@Service
@Transactional
public class UserSecurityService implements ISecurityUserService {

  private final PasswordResetTokenRepository passwordTokenRepository;

  @Autowired
  public UserSecurityService(PasswordResetTokenRepository passwordTokenRepository) {
    this.passwordTokenRepository = passwordTokenRepository;
  }

  /**
   * Checks the given token to see if it's valid or not.
   *
   * @param token the password token string
   * @return if the token is valid, returns a null string, if expired returns expiredResetToken, or if invalid, returns invalidResetToken.
   */
  @Override
  public String validatePasswordResetToken(String token) {
    final PasswordResetToken passToken = this.passwordTokenRepository.findByToken(token);

    return !this.isTokenFound(passToken) ? Defaults.PASSWORD_RESET_TOKEN_INVALID : this.isTokenExpired(passToken) ? Defaults.PASSWORD_RESET_TOKEN_EXPIRED : null;
  }

  /**
   * Checks to see if a password token exists.
   *
   * @param passToken the password token
   * @return if a password token is found, returns true
   */
  private boolean isTokenFound(PasswordResetToken passToken) {
    return passToken != null;
  }

  /**
   * Checks if the token is expired or not
   *
   * @param passToken the password token to check if expired
   * @return if the token is expired, returns true
   */
  private boolean isTokenExpired(PasswordResetToken passToken) {
    final Calendar cal = Calendar.getInstance();

    return passToken.getExpirationDate().before(cal.getTime());
  }
}
