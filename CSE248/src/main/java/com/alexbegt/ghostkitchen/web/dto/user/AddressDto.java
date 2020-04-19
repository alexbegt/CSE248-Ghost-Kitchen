package com.alexbegt.ghostkitchen.web.dto.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressDto {

  @NotNull
  @Size(min = 1, message = "{Size.addressDto.streetAddress")
  private String streetAddress;

  @NotNull
  @Size(min = 1, message = "{Size.addressDto.city")
  private String city;

  @NotNull
  @Size(min = 1, message = "{Size.addressDto.state")
  private String state;

  @NotNull
  @Size(min = 1, message = "{Size.addressDto.zipCode")
  private String zipCode;

  /**
   * Gets the street dress
   *
   * @return the street address
   */
  public String getStreetAddress() {
    return this.streetAddress;
  }

  /**
   * Sets the street address
   *
   * @param streetAddress the new street address
   */
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  /**
   * Gets the city
   *
   * @return the city
   */
  public String getCity() {
    return this.city;
  }

  /**
   * Sets the city
   *
   * @param city the new city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets the state
   *
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * Sets the state
   *
   * @param state the state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Gets the zipcode
   *
   * @return the zip code
   */
  public String getZipCode() {
    return this.zipCode;
  }

  /**
   * Sets the zip code
   *
   * @param zipCode the zip code
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Override
  public String toString() {
    return "AddressDto ["
      + "streetAddress=" + this.streetAddress
      + ", city=" + this.city
      + ", state=" + this.state
      + ", zipCode=" + this.zipCode
      + "]";
  }
}
