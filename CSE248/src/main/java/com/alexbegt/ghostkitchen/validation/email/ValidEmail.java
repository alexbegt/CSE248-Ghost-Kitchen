package com.alexbegt.ghostkitchen.validation.email;

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
 * Email validation constraint.
 *
 * @author Alexander Behrhof
 */
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidEmail {

  /**
   * Constraint violation message.
   *
   * @return Constraint violation message.
   */
  String message() default "Invalid Email";

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
