package com.alexbegt.ghostkitchen.validation.credit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator implementation for {@link ValidCreditCard}.
 *
 * @author Alexander Behrhof
 */
public class CreditCardValidator implements ConstraintValidator<ValidCreditCard, String> {

  private static final String CREDIT_CARD_REGEX = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
    "(?<mastercard>5[1-5][0-9]{14})|" +
    "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
    "(?<amex>3[47][0-9]{13})|" +
    "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
    "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

  private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile(CREDIT_CARD_REGEX);

  /**
   * Implements the validation logic.
   * The state of {@code value} must not be altered.
   * <p>
   * This method can be accessed concurrently, thread-safety must be ensured
   * by the implementation.
   *
   * @param value object to validate
   * @param context context in which the constraint is evaluated
   *
   * @return {@code false} if {@code value} does not pass the constraint
   */
  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    Matcher matcher = CREDIT_CARD_PATTERN.matcher(value);

    return matcher.matches();
  }
}
