package org.knowm.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import javax.validation.Validator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.knowm.configuration.ConfigurationException;
import org.knowm.configuration.ConfigurationFactory;
import org.knowm.configuration.ConfigurationFactoryFactory;
import org.knowm.configuration.DefaultConfigurationFactoryFactory;
import org.knowm.configuration.BaseConfigurationFactoryTest.Example;
import org.knowm.jackson.Jackson;
import org.knowm.validation.BaseValidator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationFactoryFactoryTest {

  @Rule public final ExpectedException expectedException = ExpectedException.none();

  private final ConfigurationFactoryFactory<Example> factoryFactory =
      new DefaultConfigurationFactoryFactory<>();
  private final Validator validator = BaseValidator.newValidator();

  @Test
  public void createDefaultFactory() throws Exception {

    File validFile =
        new File(getClass().getClassLoader().getResource("factory-test-valid.yml").getFile());

    ConfigurationFactory<Example> factory =
        factoryFactory.create(Example.class, validator, Jackson.newObjectMapper(), "dw");
    final Example example = factory.build(validFile);
    assertThat(example.getName()).isEqualTo("Coda Hale");
  }

  @Test
  public void createDefaultFactoryFailsUnknownProperty() throws Exception {

    File validFileWithUnknownProp =
        new File(
            getClass().getClassLoader().getResource("factory-test-unknown-property.yml").getFile());

    ConfigurationFactory<Example> factory =
        factoryFactory.create(Example.class, validator, Jackson.newObjectMapper(), "dw");
    expectedException.expect(ConfigurationException.class);
    expectedException.expectMessage("Unrecognized field at: trait");
    factory.build(validFileWithUnknownProp);
  }

  @Test
  public void createFactoryAllowingUnknownProperties() throws Exception {

    ConfigurationFactoryFactory<Example> customFactory =
        new PassThroughConfigurationFactoryFactory();

    File validFileWithUnknownProp =
        new File(
            getClass().getClassLoader().getResource("factory-test-unknown-property.yml").getFile());

    ConfigurationFactory<Example> factory =
        customFactory.create(
            Example.class,
            validator,
            Jackson.newObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES),
            "dw");
    Example example = factory.build(validFileWithUnknownProp);
    assertThat(example.getName()).isEqualTo("Mighty Wizard");
  }

  private static final class PassThroughConfigurationFactoryFactory
      extends DefaultConfigurationFactoryFactory<Example> {
    @Override
    protected ObjectMapper configureObjectMapper(ObjectMapper objectMapper) {
      return objectMapper;
    }
  }
}
