package com.alexbegt.ghostkitchen.persistence.model.user.order;

import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.util.Defaults;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "placed_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Double subTotal;

  private Double tax;

  private Double total;

  @ManyToMany(cascade = CascadeType.ALL)
  private Collection<OrderItem> orderItems;

  public Order() {

  }

  /**
   * Gets the id of the order
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id to a new id
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the orders subtotal
   *
   * @return the sub total
   */
  public Double getSubTotal() {
    return this.subTotal;
  }

  /**
   * Sets the new subtotal
   *
   * @param subTotal the subtotal
   */
  public void setSubTotal(Double subTotal) {
    this.subTotal = subTotal;
  }

  /**
   * Gets the orders tax
   *
   * @return the tax
   */
  public Double getTax() {
    return this.tax;
  }

  /**
   * Sets the orders tax
   *
   * @param tax the new tax
   */
  public void setTax(Double tax) {
    this.tax = tax;
  }

  /**
   * Gets the order total
   *
   * @return the total
   */
  public Double getTotal() {
    return this.total;
  }

  /**
   * Sets the orders total
   *
   * @param total the new total
   */
  public void setTotal(Double total) {
    this.total = total;
  }

  /**
   * Gets the list of items for the order
   *
   * @return the items for the order
   */
  public Collection<OrderItem> getOrderItems() {
    return this.orderItems;
  }

  /**
   * Sets the list of items that should be in the order
   *
   * @param items the new list of items
   */
  public void setOrderItems(Collection<OrderItem> items) {
    this.orderItems = items;

    double subtotal = 0.00;

    for (OrderItem orderItem : items) {
      Item item = orderItem.getItem();

      subtotal += (item.getPrice() * orderItem.getQuantity());
    }

    this.subTotal = subtotal;
    this.tax = this.subTotal * Defaults.TAX_PERCENT;
    this.total = this.subTotal + this.tax;
  }

  /**
   * Add a order item to the order.
   *
   * @param item the order item
   */
  public void addItem(OrderItem item) {
    this.orderItems.add(item);

    this.subTotal += (item.getItem().getPrice() + item.getQuantity());
    this.tax = this.subTotal * Defaults.TAX_PERCENT;
    this.total = this.subTotal + this.tax;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    Order order = (Order) o;

    return Objects.equals(this.id, order.id) &&
      Objects.equals(this.subTotal, order.subTotal) &&
      Objects.equals(this.tax, order.tax) &&
      Objects.equals(this.total, order.total) &&
      Objects.equals(this.orderItems, order.orderItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.subTotal, this.tax, this.total, this.orderItems);
  }

  @Override
  public String toString() {
    return "Order[" +
      "id=" + this.id +
      ", subTotal=" + this.subTotal +
      ", tax=" + this.tax +
      ", total=" + this.total +
      ']';
  }
}
