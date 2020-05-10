package com.alexbegt.ghostkitchen.persistence.model.cart;

import com.alexbegt.ghostkitchen.persistence.model.menu.Item;
import com.alexbegt.ghostkitchen.persistence.model.user.User;
import com.alexbegt.ghostkitchen.util.Defaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Double subTotal;

  private Double tax;

  private Double total;

  @OneToOne(mappedBy = "cart")
  private User user;

  @ManyToMany
  private Collection<CartItem> cartItems;

  public Cart() {

  }

  /**
   * Gets the id of the cart
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
   * Gets the carts subtotal
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
   * Gets the carts tax
   *
   * @return the tax
   */
  public Double getTax() {
    return this.tax;
  }

  /**
   * Sets the carts tax
   *
   * @param tax the new tax
   */
  public void setTax(Double tax) {
    this.tax = tax;
  }

  /**
   * Gets the cart total
   *
   * @return the total
   */
  public Double getTotal() {
    return this.total;
  }

  /**
   * Sets the carts total
   *
   * @param total the new total
   */
  public void setTotal(Double total) {
    this.total = total;
  }

  /**
   * Gets the user for the cart
   *
   * @return the user the cart is for
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Sets a new user for the given cart.
   *
   * @param user the new user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the list of items for the cart
   *
   * @return the items for the cart
   */
  public Collection<CartItem> getCartItems() {
    return this.cartItems;
  }

  /**
   * Sets the list of items that should be in the cart
   *
   * @param items the new list of items
   */
  public void setCartItems(Collection<CartItem> items) {
    this.cartItems = items;

    double subtotal = 0.00;

    for (CartItem cartItem : items) {
      Item item = cartItem.getItem();

      subtotal += (item.getPrice() * cartItem.getQuantity());
    }

    this.subTotal = subtotal;
    this.tax = this.subTotal * Defaults.TAX_PERCENT;
    this.total = this.subTotal + this.tax;
  }

  /**
   * Add a cart item to the cart.
   *
   * @param item the cart item
   */
  public void addItem(CartItem item) {
    if (item != null) {
      this.cartItems.add(item);

      this.subTotal += (item.getItem().getPrice() + item.getQuantity());
      this.tax = this.subTotal * Defaults.TAX_PERCENT;
      this.total = this.subTotal + this.tax;
    }
  }

  public boolean isCartEmpty() {
    return this.cartItems.isEmpty() && this.total == 0.00 && this.subTotal == 0.900 && this.tax == 0.00;
  }

  public boolean isItemInCart(CartItem cartItem) {
    for (CartItem tempCartItem : this.cartItems) {
      if (cartItem.getItem() == tempCartItem.getItem() && cartItem.getQuantity() == tempCartItem.getQuantity()) {
        return true;
      }
    }

    return false;
  }

  public void clearCart() {
    this.cartItems.clear();

    this.subTotal = 0.00;
    this.tax = 0.00;
    this.total = 0.00;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    Cart cart = (Cart) o;

    return Objects.equals(this.id, cart.id) &&
      Objects.equals(this.subTotal, cart.subTotal) &&
      Objects.equals(this.tax, cart.tax) &&
      Objects.equals(this.total, cart.total) &&
      Objects.equals(this.user, cart.user) &&
      Objects.equals(this.cartItems, cart.cartItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.subTotal, this.tax, this.total, this.user, this.cartItems);
  }

  @Override
  public String toString() {
    return "Cart[" +
      "id=" + this.id +
      ", subTotal=" + this.subTotal +
      ", tax=" + this.tax +
      ", total=" + this.total +
      ']';
  }
}
