package io.dropwizard.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class ConstraintViolations {

  private ConstraintViolations() { /* singleton */ }

  public static <T> String format(ConstraintViolation<T> v) {
    if (v.getConstraintDescriptor().getAnnotation() instanceof ValidationMethod) {
      return v.getMessage();
    } else {
      return String.format("%s %s", v.getPropertyPath(), v.getMessage());
    }
  }

  public static <T> List<String> format(Set<ConstraintViolation<T>> violations) {

    final Set<String> errors = new HashSet<>();
    for (ConstraintViolation<?> v : violations) {
      errors.add(format(v));
    }
    return new ArrayList<>(errors);
  }

  public static List<String> formatUntyped(Set<ConstraintViolation<?>> violations) {

    final Set<String> errors = new HashSet<>();
    for (ConstraintViolation<?> v : violations) {
      errors.add(format(v));
    }
    return new ArrayList<>(errors);
  }

  public static <T> Set<ConstraintViolation<?>> copyOf(Set<ConstraintViolation<T>> violations) {
    final Set<ConstraintViolation<?>> copiedSet = new HashSet<>();
    for (ConstraintViolation<T> violation : violations) {
      copiedSet.add(violation);
    }
    return copiedSet;
  }
}
