package io.dropwizard.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

public class PortRangeValidatorTest {
  @SuppressWarnings("PublicField")
  public static class Example {
    @PortRange
    public int port = 8080;

    @PortRange(min = 10000, max = 15000)
    public int otherPort = 10001;

    @Valid
    List<@PortRange Integer> ports = new ArrayList<>();
  }

  private final Validator validator = BaseValidator.newValidator();
  private final Example example = new Example();

  @Before
  public void setUp() throws Exception {
    assumeThat(Locale.getDefault().getLanguage(), is("en"));
  }

  @Test
  public void acceptsNonPrivilegedPorts() throws Exception {
    example.port = 2048;

    assertThat(validator.validate(example))
        .isEmpty();
  }

  @Test
  public void acceptsDynamicPorts() throws Exception {
    example.port = 0;

    assertThat(validator.validate(example))
        .isEmpty();
  }

  @Test
  public void rejectsNegativePorts() throws Exception {
    example.port = -1;

    assertThat(ConstraintViolations.format(validator.validate(example)))
        .containsOnly("port must be between 1 and 65535");
  }

  @Test
  public void allowsForCustomMinimumPorts() throws Exception {
    example.otherPort = 8080;

    assertThat(ConstraintViolations.format(validator.validate(example)))
        .containsOnly("otherPort must be between 10000 and 15000");
  }

  @Test
  public void allowsForCustomMaximumPorts() throws Exception {
    example.otherPort = 16000;

    assertThat(ConstraintViolations.format(validator.validate(example)))
        .containsOnly("otherPort must be between 10000 and 15000");
  }

  @Test
  public void rejectsInvalidPortsInList() {
    example.ports = Arrays.asList(-1);
    assertThat(ConstraintViolations.format(validator.validate(example)))
        .containsOnly("ports[0].<collection element> must be between 1 and 65535");
  }
}
