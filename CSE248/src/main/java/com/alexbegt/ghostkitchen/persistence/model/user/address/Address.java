package com.alexbegt.ghostkitchen.persistence.model.user.address;

import com.alexbegt.ghostkitchen.persistence.model.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String streetAddress;

  private String city;

  private String state;

  private String zipCode;

  @OneToOne(mappedBy = "address")
  private User user;

  public Address() {

  }

  public Address(final String streetAddress, final String city, final String state, final String zipCode) {
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
  }

  /**
   * Gets the id of the address
   *
   * @return the id of the address
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the id of the address
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the street address
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
   * Gets the city from the address
   *
   * @return the city
   */
  public String getCity() {
    return this.city;
  }

  /**
   * Sets the new city address
   *
   * @param city the new city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets the state from the address
   *
   * @return the state
   */
  public String getState() {
    return this.state;
  }

  /**
   * Sets the state on the address
   *
   * @param state the new state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * Gets the zipcode from the address
   *
   * @return the zip code
   */
  public String getZipCode() {
    return this.zipCode;
  }

  /**
   * Sets the zipcode on the address
   *
   * @param zipCode the new zip code
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * Gets the user that the address is for
   *
   * @return the user the address is for
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Sets the user that the address is for
   *
   * @param user the new user
   */
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((this.streetAddress == null) ? 0 : this.streetAddress.hashCode());

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

    final Address address = (Address) obj;

    return this.streetAddress.equalsIgnoreCase(address.streetAddress);
  }

  @Override
  public String toString() {
    return "Address [id=" + this.id +
      ", streetAddress=" + this.streetAddress +
      ", city=" + this.city +
      ", state=" + this.state +
      ", zipCode=" + this.zipCode +
      "]";
  }
}
