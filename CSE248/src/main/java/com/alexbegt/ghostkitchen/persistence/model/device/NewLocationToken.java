package com.alexbegt.ghostkitchen.persistence.model.device;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class NewLocationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String token;

  @OneToOne(targetEntity = UserLocation.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_location_id")
  private UserLocation userLocation;

  public NewLocationToken() {

  }

  public NewLocationToken(final String token, final UserLocation userLocation) {
    this.token = token;
    this.userLocation = userLocation;
  }

  /**
   * Gets the id associated with the NewLocationToken
   *
   * @return the id associated
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the id associated with the NewLocationToken
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the token on the NewLocationToken
   *
   * @return the token
   */
  public String getToken() {
    return this.token;
  }

  /**
   * Sets the new location token
   *
   * @param token the new token
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Gets the user location
   *
   * @return the User Location
   */
  public UserLocation getUserLocation() {
    return this.userLocation;
  }

  /**
   * Sets the new user location
   *
   * @param userLocation the new user location
   */
  public void setUserLocation(UserLocation userLocation) {
    this.userLocation = userLocation;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
    result = (prime * result) + ((this.token == null) ? 0 : this.token.hashCode());
    result = (prime * result) + ((this.userLocation == null) ? 0 : this.userLocation.hashCode());

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

    final NewLocationToken other = (NewLocationToken) obj;

    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    }
    else if (!this.id.equals(other.id)) {
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

    if (this.userLocation == null) {
      return other.userLocation == null;
    }
    else {
      return this.userLocation.equals(other.userLocation);
    }
  }

  @Override
  public String toString() {
    return "NewLocationToken [id=" + this.id +
      ", token=" + this.token +
      ", userLocation=" + this.userLocation +
      "]";
  }
}
