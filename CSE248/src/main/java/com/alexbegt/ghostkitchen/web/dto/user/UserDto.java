package com.alexbegt.ghostkitchen.web.dto.user;

import com.alexbegt.ghostkitchen.validation.email.ValidEmail;
import com.alexbegt.ghostkitchen.validation.password.PasswordMatches;
import com.alexbegt.ghostkitchen.validation.password.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
public class UserDto {
  @NotNull
  @Size(min = 1, message = "{Size.userDto.firstName}")
  private String firstName;

  @NotNull
  @Size(min = 1, message = "{Size.userDto.lastName}")
  private String lastName;

  @ValidEmail
  @NotNull
  @Size(min = 1, message = "{Size.userDto.email}")
  private String email;

  @NotNull
  @ValidPassword
  private String password;

  @NotNull
  @Size(min = 1)
  private String confirmedPassword;

  private boolean usingTwoFactorAuthentication = false;

  private Integer role;

  /**
   * Get's the users first name
   *
   * @return the first name
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Sets the users first name
   *
   * @param firstName the new first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Get's the users last name
   *
   * @return the last name
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Sets the users last name
   *
   * @param lastName the new last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Get's the users email
   *
   * @return The Email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Set's the users email.
   *
   * @param email the new email
   */
  public void setEmail(String email) {
    this.email = email;
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

  /**
   * Gets if the user wants to use two factor authentication or not
   *
   * @return if the user wants to use two factor authentication
   */
  public boolean getIfUsingTwoFactorAuthentication() {
    return this.usingTwoFactorAuthentication;
  }

  /**
   * Sets if the user wants two factor authentication or not.
   *
   * @param usingTwoFactorAuthentication a true or false value.
   */
  public void setUsingTwoFactorAuthentication(boolean usingTwoFactorAuthentication) {
    this.usingTwoFactorAuthentication = usingTwoFactorAuthentication;
  }

  @Override
  public String toString() {
    return "UserDto ["
      + "firstName=" + this.firstName
      + ", lastName=" + this.lastName
      + ", email=" + this.email
      + ", password=" + this.password
      + ", confirmedPassword=" + this.confirmedPassword
      + ", usingTwoFactorAuthentication=" + this.usingTwoFactorAuthentication
      + ", role=" + role
      + "]";
  }
}
