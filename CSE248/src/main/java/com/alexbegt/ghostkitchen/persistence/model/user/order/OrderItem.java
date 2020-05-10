package com.alexbegt.ghostkitchen.persistence.model.user.order;

import com.alexbegt.ghostkitchen.persistence.model.menu.Item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Item item;

  private int quantity;

  public OrderItem() {

  }

  public OrderItem(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the cart item id
   *
   * @return the CartItem ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the cart item id
   *
   * @param id the cart id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the quantity
   *
   * @return the quantity of the given cart item
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Sets the new quantity to use
   *
   * @param quantity the new quantity
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the item associated with the cart item
   *
   * @return the item
   */
  public Item getItem() {
    return this.item;
  }

  /**
   * Sets the new cart item
   *
   * @param item the new item
   */
  public void setItem(Item item) {
    this.item = item;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    OrderItem orderItem = (OrderItem) o;

    return this.quantity == orderItem.quantity &&
      Objects.equals(this.id, orderItem.id) &&
      Objects.equals(this.item, orderItem.item);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.item, this.quantity);
  }

  @Override
  public String toString() {
    return "OrderItem[" +
      "id=" + this.id +
      ", item=" + this.item +
      ", quantity=" + this.quantity +
      ']';
  }
}
