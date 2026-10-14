package com.anaptecs.jeaf.validation.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
    lEnumClass.myColors = List.of(Color.GREEN);
    lViolations = lValidator.validate(lEnumClass);
    assertEquals(1, lViolations.size());
    assertEquals(
        "[ConstraintViolationImpl{interpolatedMessage='enum literal not allowed', propertyPath=secondColor, rootBeanClass=class com.anaptecs.jeaf.validation.api.test.EnumClass, messageTemplate='enum literal not allowed'}]",
        lViolations.toString());

    lEnumClass.firstColor = Color.BLUE;
    lEnumClass.secondColor = Color.BLUE;
    lEnumClass.myColors = List.of(Color.GREEN, Color.WHITE, Color.BLUE);
    lViolations = lValidator.validate(lEnumClass);
    assertEquals(1, lViolations.size());
    assertEquals(
        "[ConstraintViolationImpl{interpolatedMessage='enum literal not allowed', propertyPath=myColors[1].<list element>, rootBeanClass=class com.anaptecs.jeaf.validation.api.test.EnumClass, messageTemplate='enum literal not allowed'}]",
        lViolations.toString());
  }
}
