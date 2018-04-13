package org.knowm.configuration;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.knowm.validation.ConstraintViolations;

/** An exception thrown where there is an error validating a configuration object. */
public class ConfigurationValidationException extends ConfigurationException {

  private final Set<ConstraintViolation<?>> constraintViolations;

  /**
   * Creates a new ConfigurationException for the given path with the given errors.
   *
   * @param path the bad configuration path
   * @param errors the errors in the path
   */
  public <T> ConfigurationValidationException(String path, Set<ConstraintViolation<T>> errors) {
    super(path, ConstraintViolations.format(errors));
    this.constraintViolations = ConstraintViolations.copyOf(errors);
  }

  /**
   * Returns the set of constraint violations in the configuration.
   *
   * @return the set of constraint violations
   */
  public Set<ConstraintViolation<?>> getConstraintViolations() {
    return constraintViolations;
  }
}
