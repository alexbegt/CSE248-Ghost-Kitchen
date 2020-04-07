package com.alexbegt.ghostkitchen.security.google2fa;

import com.alexbegt.ghostkitchen.persistence.dao.user.UserRepository;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TwoFactorAuthenticationProvider extends DaoAuthenticationProvider {

  private final UserRepository userRepository;

  @Autowired
  public TwoFactorAuthenticationProvider(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    final User user = this.userRepository.findByEmail(auth.getName());

    if ((user == null)) {
      throw new BadCredentialsException("Invalid username or password");
    }

    // to verify verification code
    if (user.isUsingTwoFactorAuthentication()) {
      final String verificationCode = ((TwoFactorWebAuthenticationDetails) auth.getDetails()).getVerificationCode();
      final Totp totp = new Totp(user.getSecret());

      if (!this.isValidLong(verificationCode) || !totp.verify(verificationCode)) {
        throw new BadCredentialsException("Invalid verification code");
      }
    }

    final Authentication result = super.authenticate(auth);

    return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
  }

  /**
   * Checks if the given verification code from the user is valid or not.
   *
   * @param code the code
   * @return if the code is valid, returns true otherwise returns false
   */
  private boolean isValidLong(String code) {
    try {
      Long.parseLong(code);
    }
    catch (final NumberFormatException e) {
      return false;
    }

    return true;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
