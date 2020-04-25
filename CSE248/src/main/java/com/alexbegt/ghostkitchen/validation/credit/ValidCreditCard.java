package com.alexbegt.ghostkitchen.validation.credit;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Credit card number validation constraint.
 *
 * @author Alexander Behrhof
 */
@Documented
@Constraint(validatedBy = CreditCardValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidCreditCard {

  /**
   * Constraint violation message.
   *
   * @return Constraint violation message.
   */
  String message() default "Invalid Credit Card";

  /**
   * Constraint groups.
   *
   * @return Constraint groups.
   */
  Class<?>[] groups() default {};

  /**
   * Constraint payload.
   *
   * @return Constraint payload.
   */
  Class<? extends Payload>[] payload() default {};
}
