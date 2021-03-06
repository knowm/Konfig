package org.knowm.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/** Checks to see that the value is one of a set of elements. */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = OneOfValidator.class)
public @interface OneOf {
  String message() default "must be one of {value}";

  Class<?>[] groups() default {};

  @SuppressWarnings("UnusedDeclaration")
  Class<? extends Payload>[] payload() default {};

  /** The set of valid values. */
  String[] value();

  /** Whether or not to ignore case. */
  boolean ignoreCase() default false;

  /** Whether or not to ignore leading and trailing whitespace. */
  boolean ignoreWhitespace() default false;
}
