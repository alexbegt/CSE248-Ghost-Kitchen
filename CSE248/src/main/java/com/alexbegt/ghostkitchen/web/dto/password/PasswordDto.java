package com.alexbegt.ghostkitchen.web.dto.password;

import com.alexbegt.ghostkitchen.validation.password.ValidPassword;

public class PasswordDto {

  private String oldPassword;

  @ValidPassword
  private String newPassword;

  private String token;

  /**
   * Get's the old password from the DTO
   *
   * @return the old password
   */
  public String getOldPassword() {
    return this.oldPassword;
  }

  /**
   * Sets the old password from the DTO
   *
   * @param oldPassword the new old password
   */
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  /**
   * Get's the new password from the DTO
   *
   * @return the new password
   */
  public String getNewPassword() {
    return this.newPassword;
  }

  /**
   * Sets the password on the DTO
   *
   * @param newPassword the new password
   */
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }


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
}
