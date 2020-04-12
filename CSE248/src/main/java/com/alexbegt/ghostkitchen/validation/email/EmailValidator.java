package com.alexbegt.ghostkitchen.validation.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator implementation for {@link ValidEmail}.
 *
 * @author Alexander Behrhof
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private static final String EMAIL_PATTERN =
    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
