package com.alexbegt.ghostkitchen.persistence.model.restaurant;

import com.alexbegt.ghostkitchen.persistence.model.menu.Item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "restaurants")
public class Restaurant {

  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String streetAddress;

  private String city;

  private String state;

  private String zipCode;

  private String phoneNumber;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "restaurants_items", joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
  private Collection<Item> items;

  public Restaurant() {
  }

  public Restaurant(final String name, final String streetAddress, final String city, final String state, final String zipCode, final String phoneNumber) {
    this.name = name;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.phoneNumber = phoneNumber;
  }

  /**
   * Get the restaurants unique id.
   *
   * @return the users unique id.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the unique id for the restaurant.
   *
   * @param id   the new unique id to use
   */
  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * Gets the phone number of the restaurant
   *
   * @return the phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the new phone number for the restaurant
   *
   * @param phoneNumber the new phone number
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Get the items the restaurants is assigned to.
   *
   * @return the items the restaurants is assigned to
   */
  public Collection<Item> getItems() {
    return this.items;
  }

  /**
   * Set the collection of items to a new set.
   *
   * @param items   the new set of items.
   */
  public void setItems(final Collection<Item> items) {
    this.items = items;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());

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

    final Restaurant restaurant = (Restaurant) obj;

    return this.name.equals(restaurant.name);
  }

  @Override
  public String toString() {
    return "Restaurant [id=" + this.id +
      ", name=" + this.name +
      ", streetAddress=" + this.streetAddress +
      ", city=" + this.city +
      ", state=" + this.state +
      ", zipCode=" + this.zipCode +
      ", phoneNumber=" + this.phoneNumber +
      ", items=" + this.items +
      "]";
  }
}
