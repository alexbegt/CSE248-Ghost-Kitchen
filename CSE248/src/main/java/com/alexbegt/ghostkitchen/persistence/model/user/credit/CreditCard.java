package com.alexbegt.ghostkitchen.persistence.model.user.credit;

import com.alexbegt.ghostkitchen.persistence.model.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class CreditCard {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String cardHolderName;

  private String creditCardNumber;

  private String expirationDate;

  private String cvv;

  @OneToOne(mappedBy = "creditCard")
  private User user;

  public CreditCard() {

  }

  public CreditCard(final String cardHolderName, final String creditCardNumber, final String expirationDate, final String cvv) {
    this.cardHolderName = cardHolderName;
    this.creditCardNumber = creditCardNumber;
    this.expirationDate = expirationDate;
    this.cvv = cvv;
  }

  /**
   * Returns the credit card's id
   *
   * @return the credit cards id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the credit cards id
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the card holders name
   *
   * @return the card holders name
   */
  public String getCardHolderName() {
    return this.cardHolderName;
  }

  /**
   * Set the credit card hold name
   *
   * @param cardHolderName the credit card holder name
   */
  public void setCardHolderName(String cardHolderName) {
    this.cardHolderName = cardHolderName;
  }

  /**
   * Gets the credit card number
   *
   * @return the credit card number
   */
  public String getCreditCardNumber() {
    return this.creditCardNumber;
  }

  /**
   * Sets the credit card number
   *
   * @param creditCardNumber the credit card number
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  /**
   * Gets the expiration date
   *
   * @return the expiration Date
   */
  public String getExpirationDate() {
    return this.expirationDate;
  }

  /**
   * Sets the expiration date
   *
   * @param expirationDate the new expiration date
   */
  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   * Gets the CVV
   *
   * @return the CVV
   */
  public String getCvv() {
    return this.cvv;
  }

  /**
   * Sets the CVV
   *
   * @param cvv the new CVV
   */
  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  /**
   * Gets the user this credit card is for
   *
   * @return the user
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Sets the user that the credit card is for
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

    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    CreditCard that = (CreditCard) o;

    return Objects.equals(this.id, that.id) &&
      Objects.equals(this.cardHolderName, that.cardHolderName) &&
      Objects.equals(this.creditCardNumber, that.creditCardNumber) &&
      Objects.equals(this.expirationDate, that.expirationDate) &&
      Objects.equals(this.cvv, that.cvv);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((this.creditCardNumber == null) ? 0 : this.creditCardNumber.hashCode());

    return result;
  }

  @Override
  public String toString() {
    return "CreditCard{" +
      "id=" + this.id +
      ", cardHolderName='" + this.cardHolderName +
      ", creditCardNumber='" + this.creditCardNumber +
      ", expirationDate='" + this.expirationDate +
      ", cvv='" + this.cvv +
      ']';
  }
}
