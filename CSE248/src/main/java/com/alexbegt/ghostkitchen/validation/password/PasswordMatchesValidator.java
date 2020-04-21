package com.alexbegt.ghostkitchen.validation.password;

import com.alexbegt.ghostkitchen.web.dto.password.ResetPasswordDto;
import com.alexbegt.ghostkitchen.web.dto.user.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator implementation for {@link PasswordMatches}.
 *
 * @author Alexander Behrhof
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

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
  public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    if(value instanceof UserDto) {
      final UserDto userDto = (UserDto) value;

      return userDto.getPassword().equals(userDto.getConfirmedPassword());
    } else if(value instanceof ResetPasswordDto) {
      final ResetPasswordDto resetPasswordDto = (ResetPasswordDto) value;

      return resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmedPassword());
    } else {
      return false;
    }
  }
}
