package com.alexbegt.ghostkitchen.persistence.model.user;

import com.alexbegt.ghostkitchen.persistence.model.cart.Cart;
import com.alexbegt.ghostkitchen.persistence.model.role.Role;
import com.alexbegt.ghostkitchen.persistence.model.user.address.Address;
import com.alexbegt.ghostkitchen.persistence.model.user.credit.CreditCard;
import com.alexbegt.ghostkitchen.persistence.model.user.order.Orders;
import org.jboss.aerogear.security.otp.api.Base32;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "user_account")
public class User {

  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  @Column(length = 60)
  private String password;

  @Column(columnDefinition = "boolean default false")
  public boolean isUsingTwoFactorAuthentication;

  private boolean enabled;

  private String secret;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinTable(name = "user_cart", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"))
  private Cart cart;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinTable(name = "user_address", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
  private Address address;

  @OneToOne
  @JoinTable(name = "user_credit_card", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "credit_card_id", referencedColumnName = "id"))
  private CreditCard creditCard;

  @OneToOne
  @JoinTable(name = "user_orders", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "orders_id", referencedColumnName = "id"))
  private Orders orders;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  public User() {
    this.secret = Base32.random();
    this.enabled = false;
  }

  /**
   * Get the users unique id.
   *
   * @return the users unique id.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the unique id for the account.
   *
   * @param id   the new unique id to use
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Gets the users first name.
   *
   * @return the users first name.
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Set the user's first name.
   *
   * @param firstName   the new first name to use.
   */
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the users last name.
   *
   * @return the users last name.
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Set the user's last name.
   *
   * @param lastName   the new last name to use.
   */
  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the users email.
   *
   * @return the users emaii.
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Set the user's email.
   *
   * @param email   the new email to use.
   */
  public void setEmail(final String email) {
    this.email = email;
  }

  /**
   * Gets the users password (ENCRYPTED).
   *
   * @return the users password (ENCRYPTED).
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Sets the users password
   *
   * @param password  the new password
   */
  public void setPassword(final String password) {
    this.password = password;
  }

  /**
   * Get's if the users account is enabled or not.
   *
   * @return true if user is enabled, false if it isn't
   */
  public boolean isEnabled() {
    return this.enabled;
  }

  /**
   * Sets if the user account is enabled or not.
   *
   * @param enabled true or false
   */
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Get if the user is using two factor authentication or not.
   *
   * @return TRUE if using two factor, FALSE if not.
   */
  public boolean isUsingTwoFactorAuthentication() {
    return this.isUsingTwoFactorAuthentication;
  }

  /**
   * Set if the user is using two factor authentication or not.
   *
   * @param isUsingTwoFactorAuthentication     true or false
   */
  public void setUsingTwoFactorAuthentication(boolean isUsingTwoFactorAuthentication) {
    this.isUsingTwoFactorAuthentication = isUsingTwoFactorAuthentication;
  }

  /**
   * Get the secret used for two factor authentication.
   *
   * @return the secret.
   */
  public String getSecret() {
    return this.secret;
  }

  /**
   * Puts a new secret code on the users account.
   *
   * @param secret    The new secret to use for two factor authentication.
   */
  public void setSecret(String secret) {
    this.secret = secret;
  }

  /**
   * Gets the current cart of the user
   *
   * @return the current cart of the user
   */
  public Cart getCart() {
    return this.cart;
  }

  /**
   * Sets the users cart
   *
   * @param cart the new cart
   */
  public void setCart(Cart cart) {
    this.cart = cart;
  }

  /**
   * Gets the users address
   *
   * @return the users address
   */
  public Address getAddress() {
    return this.address;
  }

  /**
   * Sets the users address
   *
   * @param address the new address
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * Gets the users credit card
   *
   * @return the users credit card
   */
  public CreditCard getCreditCard() {
    return creditCard;
  }

  /**
   * Sets the users credit card
   *
   * @param creditCard the new credit card
   */
  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

  /**
   * Gets the orders the user has placed.
   *
   * @return the orders
   */
  public Orders getOrders() {
    return this.orders;
  }

  /**
   * Sets the orders that the user has placed.
   *
   * @param orders the new orders
   */
  public void setOrders(Orders orders) {
    this.orders = orders;
  }

  /**
   * Gets the roles the user has assigned to it.
   *
   * @return the collection of roles the account has.
   */
  public Collection<Role> getRoles() {
    return this.roles;
  }

  /**
   * Sets the roles assigned to a account.
   *
   * @param roles   the new set of roles
   */
  public void setRoles(final Collection<Role> roles) {
    this.roles = roles;
  }

  /**
   * Gets the full name of the user.
   *
   * @return the full name of the user
   */
  public String getFullName() {
    return this.getFirstName() + " " + this.getLastName();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((this.email == null) ? 0 : this.email.hashCode());

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

    final User user = (User) obj;

    return this.email.equals(user.email);
  }

  @Override
  public String toString() {
    return "User [id=" + this.id +
      ", firstName=" + this.firstName +
      ", lastName=" + this.lastName +
      ", email=" + this.email +
      ", password=" + this.password +
      ", enabled=" + this.enabled +
      ", isUsingTwoFactorAuthentication=" + this.isUsingTwoFactorAuthentication +
      ", secret=" + this.secret +
      ", roles=" + this.roles +
      ", cart=" + this.cart +
      ", address=" + this.address +
      ", creditCard=" + this.creditCard +
      ", orders=" + this.orders +
      "]";
  }
}
