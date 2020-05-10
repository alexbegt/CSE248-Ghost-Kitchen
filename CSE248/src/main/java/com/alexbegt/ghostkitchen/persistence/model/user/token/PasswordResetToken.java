package com.alexbegt.ghostkitchen.persistence.model.user.token;

import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.util.Defaults;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String token;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  private Date expirationDate;

  public PasswordResetToken() {

  }

  public PasswordResetToken(final String token) {
    this.token = token;
    this.expirationDate = this.calculateExpirationDate();
  }

  public PasswordResetToken(final String token, final User user) {
    this.token = token;
    this.user = user;
    this.expirationDate = this.calculateExpirationDate();
  }

  /**
   * Get the unique id.
   *
   * @return the unique id.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the unique id for the password reset token.
   *
   * @param id   the new unique id to use
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Gets the password reset token's token.
   *
   * @return the token
   */
  public String getToken() {
    return this.token;
  }

  /**
   * Sets the password reset token
   *
   * @param token  the new token
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Gets the user assigned to this password reset token.
   *
   * @return the user
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Sets the user that the password reset token is for.
   *
   * @param user  the new user to assign to the token.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the date that the password reset token expires.
   *
   * @return the expiration date
   */
  public Date getExpirationDate() {
    return this.expirationDate;
  }

  /**
   * Calculates the expiration date of the password reset token.
   *
   * @return the calculated expiration date
   */
  private Date calculateExpirationDate() {
    final Calendar cal = Calendar.getInstance();

    cal.setTimeInMillis(new Date().getTime());
    cal.add(Calendar.MINUTE, Defaults.TOKEN_EXPIRATION_TIME);

    return new Date(cal.getTime().getTime());
  }

  /**
   * Updates the password reset token with a new token and recalculates an expiration date.
   *
   * @param token  the new token to use
   */
  public void updateToken(final String token) {
    this.token = token;
    this.expirationDate = this.calculateExpirationDate();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + ((this.expirationDate == null) ? 0 : this.expirationDate.hashCode());
    result = prime * result + ((this.token == null) ? 0 : this.token.hashCode());
    result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());

    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    final PasswordResetToken other = (PasswordResetToken) obj;

    if (this.expirationDate == null) {
      if (other.expirationDate != null) {
        return false;
      }
    }
    else if (!this.expirationDate.equals(other.expirationDate)) {
      return false;
    }

    if (this.token == null) {
      if (other.token != null) {
        return false;
      }
    }
    else if (!this.token.equals(other.token)) {
      return false;
    }

    if (this.user == null) {
      return other.user == null;
    }
    else {
      return this.user.equals(other.user);
    }
  }

  @Override
  public String toString() {
    return "PasswordResetToken [String=" + this.token + "]" +
      "[Expires" + this.expirationDate +
      "]";
  }
}
