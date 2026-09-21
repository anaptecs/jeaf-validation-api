package com.anaptecs.jeaf.validation.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

public class EnumSubsetValidationTest {
  @Test
  void testEnumSubsetValidation( ) {
    Validator lValidator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<EnumClass>> lViolations;
    EnumClass lEnumClass = new EnumClass();
    lEnumClass.firstColor = Color.BLUE;
    lEnumClass.secondColor = Color.BLUE;
    lViolations = lValidator.validate(lEnumClass);
    assertTrue(lViolations.isEmpty());

    lEnumClass.firstColor = Color.GREEN;
    lEnumClass.secondColor = Color.BLUE;
    lViolations = lValidator.validate(lEnumClass);
    assertEquals(1, lViolations.size());
    assertEquals(
        "[ConstraintViolationImpl{interpolatedMessage='enum literal not allowed', propertyPath=firstColor, rootBeanClass=class com.anaptecs.jeaf.validation.api.test.EnumClass, messageTemplate='enum literal not allowed'}]",
        lViolations.toString());

    lEnumClass.firstColor = Color.BLUE;
    lEnumClass.secondColor = Color.GREEN;
    lViolations = lValidator.validate(lEnumClass);
    assertEquals(1, lViolations.size());
    assertEquals(
        "[ConstraintViolationImpl{interpolatedMessage='enum literal not allowed', propertyPath=secondColor, rootBeanClass=class com.anaptecs.jeaf.validation.api.test.EnumClass, messageTemplate='enum literal not allowed'}]",
        lViolations.toString());
  }
}
