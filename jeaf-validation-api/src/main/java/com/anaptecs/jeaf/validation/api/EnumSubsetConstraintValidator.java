/**
 * Copyright 2004 - 2018 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.validation.api;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Class implements a {@link ConstraintValidator} for {@link EnumSubset} constraint.
 *
 * @author JEAF Development Team
 */
public class EnumSubsetConstraintValidator implements jakarta.validation.ConstraintValidator<EnumSubset, Enum<?>> {
  /**
   * List with the names of all literals that are explicitly allowed.
   */
  private List<String> allowedLiterals;

  /**
   * List with the names of all literals that are explicitly not allowed.
   */
  private List<String> notAllowedLiterals;

  @Override
  public void initialize(EnumSubset pConstraintAnnotation) {
    allowedLiterals = List.of(pConstraintAnnotation.allowedLiterals());
    notAllowedLiterals = List.of(pConstraintAnnotation.notAllowedLiterals());

    if (allowedLiterals.isEmpty() && notAllowedLiterals.isEmpty()
        || !allowedLiterals.isEmpty() && !notAllowedLiterals.isEmpty()) {
      throw new IllegalArgumentException("Either 'allowedLiterals' or 'notAllowedLiterals' must be set");
    }
  }

  @Override
  public boolean isValid(Enum<?> pValue, ConstraintValidatorContext pContext) {
    boolean lValid;
    if (pValue != null) {
      if (allowedLiterals.size() > 0) {
        lValid = allowedLiterals.contains(pValue.name());
      }
      else {
        lValid = !notAllowedLiterals.contains(pValue.name());
      }
    }
    // Similar to other constraints "null" is valid.
    else {
      lValid = true;
    }
    return lValid;
  }
}
