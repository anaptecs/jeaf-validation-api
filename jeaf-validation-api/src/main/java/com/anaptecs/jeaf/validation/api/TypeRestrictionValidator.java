/**
 * Copyright 2004 - 2018 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.validation.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Class implements a {@link ConstraintValidator} for {@link EnumSubset} constraint.
 *
 * @author JEAF Development Team
 */
public class TypeRestrictionValidator implements jakarta.validation.ConstraintValidator<TypeRestriction, Object> {
  /**
   * List with the names of all types that are explicitly allowed.
   */
  private List<Class<?>> allowedTypes;

  /**
   * List with the names of all types that are explicitly not allowed.
   */
  private List<Class<?>> notAllowedTypes;

  /**
   * Flag defines if subtypes of the configured allowed types should be treated as a match as well. In case of
   * {@code true} subclasses of a configured type are treated the same way as the configured type itself.
   */
  private boolean allowSubtypes;

  @Override
  public void initialize(TypeRestriction pConstraintAnnotation) {
    allowSubtypes = pConstraintAnnotation.allowSubtypes();

    // Resolve class objects for allowed types.
    if (pConstraintAnnotation.allowedTypes().length > 0) {
      allowedTypes = Arrays.asList(pConstraintAnnotation.allowedTypes());
    }
    else {
      allowedTypes = Collections.emptyList();
    }

    // Resolve class objects for not allowed types.
    if (pConstraintAnnotation.notAllowedTypes().length > 0) {
      notAllowedTypes = Arrays.asList(pConstraintAnnotation.notAllowedTypes());
    }
    else {
      notAllowedTypes = Collections.emptyList();
    }

    if (allowedTypes.isEmpty() && notAllowedTypes.isEmpty()
        || !allowedTypes.isEmpty() && !notAllowedTypes.isEmpty()) {
      throw new IllegalArgumentException("Either 'allowedTypes' or 'notAllowedTypes' must be set");
    }
  }

  @Override
  public boolean isValid(Object pValue, ConstraintValidatorContext pContext) {
    boolean lValid;
    if (pValue != null) {
      Class<?> lValueClass = pValue.getClass();
      if (allowedTypes.size() > 0) {
        // allowSubtypes is only relevant for allowedTypes.
        lValid = isTypeMatch(lValueClass, allowedTypes, allowSubtypes);
      }
      else {
        // notAllowedTypes are always matched exactly, independent of allowSubtypes.
        lValid = !isTypeMatch(lValueClass, notAllowedTypes, false);
      }
    }
    // Similar to other constraints "null" is valid.
    else {
      lValid = true;
    }
    return lValid;
  }

  /**
   * Method checks if the passed class matches one of the classes in the passed list. Depending on
   * {@code pAllowSubtypes} either an exact type match is required or the passed class may also be a subtype
   * of one of the configured types.
   *
   * @param pValueClass Class that should be checked. The parameter must not be null.
   * @param pTypes List of types against which the passed class should be checked. The parameter must not be null.
   * @param pAllowSubtypes Flag defines if subtypes of the configured types should be considered as a match as
   * well or if an exact type match is required.
   * @return boolean true if the passed class matches at least one of the types in the passed list.
   */
  private static boolean isTypeMatch(Class<?> pValueClass, List<Class<?>> pTypes, boolean pAllowSubtypes) {
    boolean lMatch;
    if (pAllowSubtypes) {
      lMatch = false;
      for (Class<?> lNextType : pTypes) {
        if (lNextType.isAssignableFrom(pValueClass)) {
          lMatch = true;
          break;
        }
      }
    }
    else {
      lMatch = pTypes.contains(pValueClass);
    }
    return lMatch;
  }
}
