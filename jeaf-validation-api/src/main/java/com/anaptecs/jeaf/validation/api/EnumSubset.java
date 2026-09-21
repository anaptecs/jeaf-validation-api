package com.anaptecs.jeaf.validation.api;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * This annotation can be used to further restrict the valid values of an enum property.<br>
 * <br>
 * This can be achieved either by defining an explicit list of allowed literals via {@link #allowedLiterals()} or by
 * specifying literals that are not permitted via {@link #notAllowedLiterals()}. Only one of these options may be used
 * at a time; they cannot be used simultaneously.
 *
 * @author JEAF Development Team
 */
@Documented
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { EnumSubsetConstraintValidator.class })
public @interface EnumSubset {
  /**
   * Names of the literals that are allowed.
   */
  String[] allowedLiterals() default {};

  /**
   * Names of the literals that are not allowed.
   */
  String[] notAllowedLiterals() default {};

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String message() default "enum literal not allowed";

}
