package com.alexbegt.ghostkitchen.persistence.model.device;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class DeviceMetadata {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long userId;
  private String deviceDetails;
  private String location;
  private Date lastLoggedIn;

  /**
   * Gets the id associated with the device metadata
   *
   * @return the id associated with the DeviceMetadata
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id associated with the device metadata
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the user Id associated with the device metadata
   *
   * @return the user id
   */
  public Long getUserId() {
    return this.userId;
  }

  /**
   * Sets the user Id associated with the device metadata
   *
   * @param userId the new user id
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Gets the device details
   *
   * @return the device details
   */
  public String getDeviceDetails() {
    return this.deviceDetails;
  }

  /**
   * Sets the device details on the device metadata
   *
   * @param deviceDetails the new device details
   */
  public void setDeviceDetails(String deviceDetails) {
    this.deviceDetails = deviceDetails;
  }

  /**
   * Gets the location on the device details
   *
   * @return the location
   */
  public String getLocation() {
    return this.location;
  }

  /**
   * Sets the location on the device details
   *
   * @param location the new location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Gets the last login date on the device details
   *
   * @return the last login date
   */
  public Date getLastLoggedIn() {
    return this.lastLoggedIn;
  }

  /**
   * Sets the last login date on the device details
   *
   * @param lastLoggedIn the new login date
   */
  public void setLastLoggedIn(Date lastLoggedIn) {
    this.lastLoggedIn = lastLoggedIn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    DeviceMetadata that = (DeviceMetadata) o;

    return Objects.equals(this.id, that.id) &&
      Objects.equals(this.userId, that.userId) &&
      Objects.equals(this.deviceDetails, that.deviceDetails) &&
      Objects.equals(this.location, that.location) &&
      Objects.equals(this.lastLoggedIn, that.lastLoggedIn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.userId, this.deviceDetails, this.location, this.lastLoggedIn);
  }

  @Override
  public String toString() {
    return "DeviceMetadata{" + "id=" + this.id
      + ", userId=" + this.userId
      + ", deviceDetails='" + this.deviceDetails + '\''
      + ", location='" + this.location + '\''
      + ", lastLoggedIn=" + this.lastLoggedIn
      + '}';
  }
}
