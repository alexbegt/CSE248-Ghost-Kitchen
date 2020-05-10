package com.alexbegt.ghostkitchen.persistence.model.menu;

import com.alexbegt.ghostkitchen.persistence.model.cart.Cart;
import com.alexbegt.ghostkitchen.persistence.model.restaurant.Restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "items")
public class Item {

  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String description;

  private Double price;

  private String image;

  @ManyToMany(mappedBy = "items")
  private Collection<Restaurant> restaurants;

  public Item() {
  }

  public Item(final String name, final String description, final Double price, final String image) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.image = image;
  }

  /**
   * Get the items unique id.
   *
   * @return the items unique id.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the unique id for the item.
   *
   * @param id   the new unique id to use
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the item
   *
   * @return the name of the item
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the item
   *
   * @param name the new name to use
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the description of the item
   *
   * @return the description of the item
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description for the modification
   *
   * @param description the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the items price.
   *
   * @return the items price.
   */
  public Double getPrice() {
    return this.price;
  }

  /**
   * Sets the price for the item.
   *
   * @param price the new price to use
   */
  public void setPrice(final Double price) {
    this.price = price;
  }

  /**
   * Gets the items image.
   *
   * @return the items image.
   */
  public String getImage() {
    return image;
  }

  /**
   * Sets the image for the item.
   *
   * @param image the new image to use
   */
  public void setImage(String image) {
    this.image = image;
  }

  /**
   * Gets the list of restaurants assigned to the item.
   *
   * @return the list of restaurants the item is assigned to
   */
  public Collection<Restaurant> getRestaurants() {
    return this.restaurants;
  }

  /**
   * Sets the new restaurants that the item is assigned to.
   *
   * @param restaurants the new collection of restaurants to use
   */
  public void setRestaurants(Collection<Restaurant> restaurants) {
    this.restaurants = restaurants;
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

    final Item item = (Item) obj;

    return this.name.equals(item.name);
  }

  @Override
  public String toString() {
    return "Item [id=" + this.id +
      ", name=" + this.name +
      ", price=" + this.price +
      ", image=" + this.image +
      "]";
  }

}
