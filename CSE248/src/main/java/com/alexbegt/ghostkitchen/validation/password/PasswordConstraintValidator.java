package com.alexbegt.ghostkitchen.validation.password;

import com.google.common.base.Joiner;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Validator implementation for {@link ValidPassword}.
 *
 * @author Alexander Behrhof
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

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
    final PasswordValidator validator = new PasswordValidator(Arrays.asList(
      new LengthRule(8, 30),
      new CharacterRule(EnglishCharacterData.UpperCase, 1),
      new CharacterRule(EnglishCharacterData.Digit, 1),
      new CharacterRule(EnglishCharacterData.Special, 1),
      new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
      new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
      new IllegalSequenceRule(EnglishSequenceData.USQwerty, 3, false),
      new WhitespaceRule()));

    final RuleResult result = validator.validate(new PasswordData(value));

    if (result.isValid()) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(Joiner.on(", ").join(validator.getMessages(result))).addConstraintViolation();

    return false;
  }
}
