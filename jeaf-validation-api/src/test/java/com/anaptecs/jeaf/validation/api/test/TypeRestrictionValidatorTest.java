package com.anaptecs.jeaf.validation.api.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.annotation.Annotation;

import com.anaptecs.jeaf.validation.api.TypeRestriction;
import com.anaptecs.jeaf.validation.api.TypeRestrictionValidator;
import jakarta.validation.Payload;
import org.junit.jupiter.api.Test;

public class TypeRestrictionValidatorTest {

  @Test
  void testAllowedTypesSupportsSubtypesByDefault() {
    TypeRestrictionValidator lValidator = new TypeRestrictionValidator();
    lValidator.initialize(typeRestriction(new Class<?>[] { Animal.class }, new Class<?>[0]));

    assertTrue(lValidator.isValid(new Animal(), null));
    assertTrue(lValidator.isValid(new Dog(), null));
    assertTrue(lValidator.isValid(new Poodle(), null));
    assertFalse(lValidator.isValid("not an animal", null));
    // Similar to other constraints "null" is always valid.
    assertTrue(lValidator.isValid(null, null));
  }

  @Test
  void testAllowedTypesWithoutSubtypesRequiresExactMatch() {
    TypeRestrictionValidator lValidator = new TypeRestrictionValidator();
    lValidator.initialize(typeRestriction(new Class<?>[] { Animal.class }, new Class<?>[0], false));

    assertTrue(lValidator.isValid(new Animal(), null));
    assertFalse(lValidator.isValid(new Dog(), null));
    assertFalse(lValidator.isValid(new Poodle(), null));
  }

  @Test
  void testNotAllowedTypesAlwaysUsesExactMatchIgnoringAllowSubtypesFlag() {
    TypeRestrictionValidator lValidator = new TypeRestrictionValidator();
    for (boolean lAllowSubtypes : new boolean[] { true, false }) {
      lValidator
          .initialize(typeRestriction(new Class<?>[0], new Class<?>[] { Animal.class }, lAllowSubtypes));

      assertFalse(lValidator.isValid(new Animal(), null));
      // notAllowedTypes always uses exact-type matching, independent of allowSubtypes.
      assertTrue(lValidator.isValid(new Dog(), null));
      assertTrue(lValidator.isValid(new Poodle(), null));
      // Similar to other constraints "null" is always valid.
      assertTrue(lValidator.isValid(null, null));
    }
  }


  @Test
  void testInitializeFailsWhenBothTypeListsEmpty() {
    TypeRestrictionValidator lValidator = new TypeRestrictionValidator();

    IllegalArgumentException lException = assertThrows(IllegalArgumentException.class,
        () -> lValidator.initialize(typeRestriction(new Class<?>[0], new Class<?>[0])));

    assertTrue(lException.getMessage().contains("Either 'allowedTypes' or 'notAllowedTypes' must be set"));
  }

  @Test
  void testInitializeFailsWhenBothTypeListsAreConfigured() {
    TypeRestrictionValidator lValidator = new TypeRestrictionValidator();

    IllegalArgumentException lException = assertThrows(IllegalArgumentException.class,
        () -> lValidator.initialize(typeRestriction(new Class<?>[] { String.class },
            new Class<?>[] { Integer.class })));

    assertTrue(lException.getMessage().contains("Either 'allowedTypes' or 'notAllowedTypes' must be set"));
  }

  private static TypeRestriction typeRestriction(Class<?>[] pAllowedTypes, Class<?>[] pNotAllowedTypes) {
    return typeRestriction(pAllowedTypes, pNotAllowedTypes, true);
  }

  private static TypeRestriction typeRestriction(Class<?>[] pAllowedTypes, Class<?>[] pNotAllowedTypes,
      boolean pAllowSubtypes) {
    return new TypeRestriction() {
      @Override
      public Class<?>[] allowedTypes() {
        return pAllowedTypes;
      }

      @Override
      public Class<?>[] notAllowedTypes() {
        return pNotAllowedTypes;
      }

      @Override
      public boolean allowSubtypes() {
        return pAllowSubtypes;
      }

      @Override
      public Class<?>[] groups() {
        return new Class<?>[0];
      }

      @Override
      public Class<? extends Payload>[] payload() {
        return emptyPayloadArray();
      }

      @Override
      public String message() {
        return "enum literal not allowed";
      }

      @Override
      public Class<? extends Annotation> annotationType() {
        return TypeRestriction.class;
      }
    };
  }

  @SuppressWarnings("unchecked")
  private static Class<? extends Payload>[] emptyPayloadArray() {
    return (Class<? extends Payload>[]) new Class<?>[0];
  }

  private static class Animal {
  }

  private static class Dog extends Animal {
  }

  private static class Poodle extends Dog {
  }
}
