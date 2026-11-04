package com.anaptecs.jeaf.validation.api;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.anaptecs.jeaf.validation.api.TypeRestriction.List;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * This annotation can be used to further restrict the allowed types within a polymorphic property to certain types.<br>
 * <br>
 * This can be achieved either by defining an explicit list of allowed types via {@link #allowedTypes()} or by
 * specifying types that are not permitted via {@link #notAllowedTypes()}. Only one of these options may be used at a
 * time; they cannot be used simultaneously.<br>
 * <br>
 * By default subtypes of a configured allowed type are treated the same way as the configured type itself (e.g. if
 * {@code Animal} is configured as allowed type then {@code Dog} as a subclass of {@code Animal} is allowed as well).
 * This behavior can be disabled via {@link #allowSubtypes()} so that only objects whose exact runtime type matches
 * one of the configured allowed types are taken into account.<br>
 * <br>
 * {@link #notAllowedTypes()} are always matched using the exact runtime type of the validated object.
 * {@link #allowSubtypes()} has no effect on {@link #notAllowedTypes()}.
 *
 * @author JEAF Development Team
 */
@Documented
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Constraint(validatedBy = { TypeRestrictionValidator.class })
public @interface TypeRestriction {
  /**
   * Allowed types.
   */
  Class<?>[] allowedTypes() default {};

  /**
   * Not allowed types. Matching is always based on the exact runtime type of the validated object,
   * independent of {@link #allowSubtypes()}.
   */
  Class<?>[] notAllowedTypes() default {};

  /**
   * Flag defines if subtypes of the configured {@link #allowedTypes()} should be treated as a match as well. This
   * flag has no effect on {@link #notAllowedTypes()} which are always matched using the exact runtime type.<br>
   * <br>
   * If set to {@code true} (default) subclasses of a configured allowed type are treated the same way as the
   * configured type itself. If set to {@code false} only objects whose exact runtime type matches one of the
   * configured allowed types are taken into account, so subclasses of a configured type are not considered
   * automatically.
   */
  boolean allowSubtypes() default true;

  Class<?>[] groups() default {};


  Class<? extends Payload>[] payload() default {};

  String message() default "enum literal not allowed";

  @Documented
  @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
  @Retention(RUNTIME)
  @interface List {
    TypeRestriction[] value();
  }
}
