package org.knowm.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.Test;
import org.knowm.configuration.ConfigurationValidationException;
import org.knowm.validation.BaseValidator;

public class ConfigurationValidationExceptionTest {
  private static class Example {
    @NotNull String woo;
  }

  private ConfigurationValidationException e;

  @Before
  public void setUp() throws Exception {
    assumeThat(Locale.getDefault().getLanguage(), is("en"));

    final Validator validator = BaseValidator.newValidator();
    final Set<ConstraintViolation<Example>> violations = validator.validate(new Example());
    this.e = new ConfigurationValidationException("config.yml", violations);
  }

  @Test
  public void formatsTheViolationsIntoAHumanReadableMessage() throws Exception {
    assertThat(e.getMessage())
        .isEqualTo(String.format("config.yml has an error:%n" + "  * woo may not be null%n"));
  }

  @Test
  public void retainsTheSetOfExceptions() throws Exception {
    assertThat(e.getConstraintViolations()).isNotEmpty();
  }
}
