package com.alexbegt.ghostkitchen.web.dto.password;

import com.alexbegt.ghostkitchen.validation.password.PasswordMatches;
import com.alexbegt.ghostkitchen.validation.password.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
public class ResetPasswordDto {

  @NotNull
  @ValidPassword
  private String password;

  @NotNull
  @Size(min = 1)
  @ValidPassword
  private String confirmedPassword;

  private String token;

  /**
   * Get's the token from the DTO
   *
   * @return the token
   */
  public String getToken() {
    return this.token;
  }

  /**
   * Sets the token on the DTO
   *
   * @param token the token
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Get's the users password
   *
   * @return the password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Set's the users password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get's the users confirmed password
   *
   * @return the confirmed password
   */
  public String getConfirmedPassword() {
    return this.confirmedPassword;
  }

  /**
   * Set's the users confirmed password.
   *
   * @param confirmedPassword the new confirmed password
   */
  public void setConfirmedPassword(String confirmedPassword) {
    this.confirmedPassword = confirmedPassword;
  }
}
