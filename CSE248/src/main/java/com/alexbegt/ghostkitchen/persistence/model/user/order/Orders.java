package com.alexbegt.ghostkitchen.persistence.model.user.order;

import com.alexbegt.ghostkitchen.persistence.model.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Orders {

  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private LocalDateTime firstOrderDate;

  private LocalDateTime latestOrderDate;

  private int numberOfOrders;

  @OneToMany
  private Collection<Order> placedOrders;

  @OneToOne(mappedBy = "orders")
  private User user;

  /**
   * Gets the id of the order
   *
   * @return the id of the order
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Gets the id of the order
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the time and date when the first order was placed.
   *
   * @return the first time an order was placed
   */
  public LocalDateTime getFirstOrderDate() {
    return this.firstOrderDate;
  }

  /**
   * Sets the first order date
   *
   * @param firstOrderDate the new order date
   */
  public void setFirstOrderDate(LocalDateTime firstOrderDate) {
    this.firstOrderDate = firstOrderDate;
  }

  /**
   * Gets the latest order date
   *
   * @return the latest order date
   */
  public LocalDateTime getLatestOrderDate() {
    return this.latestOrderDate;
  }

  /**
   * Sets the latest order date.
   *
   * @param latestOrderDate the latest order date
   */
  public void setLatestOrderDate(LocalDateTime latestOrderDate) {
    this.latestOrderDate = latestOrderDate;
  }

  /**
   * Gets the number of orders the user has placed
   *
   * @return the number of orders
   */
  public int getNumberOfOrders() {
    return this.numberOfOrders;
  }

  /**
   * Sets the number of orders the user has placed
   *
   * @param numberOfOrders the new number of orders
   */
  public void setNumberOfOrders(int numberOfOrders) {
    this.numberOfOrders = numberOfOrders;
  }

  /**
   * Gets the list of carts the user has checked out with
   *
   * @return the collection of carts
   */
  public Collection<Order> getPlacedOrders() {
    return this.placedOrders;
  }

  /**
   * Sets a new set of orders
   *
   * @param orders the new set of orders
   */
  public void setPlacedOrders(Collection<Order> orders) {
    this.placedOrders = orders;

    this.numberOfOrders = orders.size();
  }

  /**
   *
   * @param order the order to add
   */
  public void addOrder(Order order) {
    if (this.placedOrders != null) {
      if (!this.placedOrders.contains(order)) {
        this.placedOrders.add(order);
        this.numberOfOrders = this.placedOrders.size();
        this.latestOrderDate = LocalDateTime.now();
      }
    }
    else {
      this.placedOrders = new ArrayList<>();
      this.firstOrderDate = LocalDateTime.now();
      this.placedOrders.add(order);
      this.numberOfOrders = this.placedOrders.size();
    }
  }

  /**
   * Gets the user from the set of orders
   *
   * @return the user
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Sets a new user to use on the order
   *
   * @param user the new user
   */
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Orders orders = (Orders) o;

    return this.numberOfOrders == orders.numberOfOrders &&
      Objects.equals(this.id, orders.id) &&
      Objects.equals(this.firstOrderDate, orders.firstOrderDate) &&
      Objects.equals(this.latestOrderDate, orders.latestOrderDate) &&
      Objects.equals(this.placedOrders, orders.placedOrders);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((this.firstOrderDate == null) ? 0 : this.firstOrderDate.hashCode());

    return result;
  }

  @Override
  public String toString() {
    return "Orders{" +
      "id=" + this.id +
      ", firstOrderDate=" + this.firstOrderDate +
      ", latestOrderDate=" + this.latestOrderDate +
      ", numberOfOrders=" + this.numberOfOrders +
      '}';
  }
}
