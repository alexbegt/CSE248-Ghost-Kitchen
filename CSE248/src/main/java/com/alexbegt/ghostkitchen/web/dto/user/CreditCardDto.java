package com.alexbegt.ghostkitchen.web.dto.user;

import com.alexbegt.ghostkitchen.validation.credit.ValidCreditCard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreditCardDto {

  @NotNull
  @Size(min = 1, message = "{Size.creditCardDto.cardHolderName")
  private String cardHolderName;

  @NotNull
  @Size(min = 1, message = "{Size.creditCardDto.creditCardNumber")
  @ValidCreditCard
  private String creditCardNumber;

  @NotNull
  @Size(min = 1, message = "{Size.creditCardDto.expirationDate")
  private String expirationDate;

  @NotNull
  @Size(min = 1, message = "{Size.creditCardDto.cvv")
  private String cvv;

  /**
   * Gets the credit card holders name
   *
   * @return the credit card holders name
   */
  public String getCardHolderName() {
    return this.cardHolderName;
  }

  /**
   * Sets the credit card holder name
   *
   * @param cardHolderName the new credit card holder name
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
   * @return the expiration date
   */
  public String getExpirationDate() {
    return this.expirationDate;
  }

  /**
   * Sets the new expiration date
   *
   * @param expirationDate the expiration date
   */
  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   * Gets the cvv
   *
   * @return the cvv
   */
  public String getCvv() {
    return this.cvv;
  }

  /**
   * Sets a new CVV
   *
   * @param cvv the new cvv
   */
  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

  @Override
  public String toString() {
    return "CreditCardDto ["
      + "cardHolderName=" + this.cardHolderName
      + ", creditCardNumber=" + this.creditCardNumber
      + ", expirationDate=" + this.expirationDate
      + ", cvv=" + this.cvv
      + "]";
  }
}
