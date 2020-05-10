package com.alexbegt.ghostkitchen.persistence.model.device;

import com.alexbegt.ghostkitchen.persistence.model.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserLocation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String country;

  private boolean enabled;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private User user;

  public UserLocation() {
    this.enabled = false;
  }

  public UserLocation(String country, User user) {
    this.country = country;
    this.user = user;
    this.enabled = false;
  }

  /**
   * Get's the id of the UserLocation
   *
   * @return the id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the id of the user location
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the country associated with the user location
   *
   * @return the country associated with the user location
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * Sets the country on the user location
   *
   * @param country the new country
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Gets the user from the user location
   *
   * @return the user
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Sets the user on the user location
   *
   * @param user the new user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets if the user location is enabled or not.
   *
   * @return if the user location is enabled or not
   */
  public boolean isEnabled() {
    return this.enabled;
  }

  /**
   * Sets if the user location is enabled or not.
   *
   * @param enabled if the user location is enabled or not
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = (prime * result) + ((this.country == null) ? 0 : this.country.hashCode());
    result = (prime * result) + (this.enabled ? 1231 : 1237);
    result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
    result = (prime * result) + ((this.user == null) ? 0 : this.user.hashCode());

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    final UserLocation other = (UserLocation) obj;

    if (this.country == null) {
      if (other.country != null) {
        return false;
      }
    }
    else if (!this.country.equals(other.country)) {
      return false;
    }

    if (this.enabled != other.enabled) {
      return false;
    }

    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!this.id.equals(other.id)) {
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
    return "UserLocation [id=" + this.id +
      ", country=" + this.country +
      ", enabled=" + this.enabled +
      ", user=" + this.user +
      "]";
  }
}
