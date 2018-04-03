package org.knowm.konfig;

import java.io.IOException;

import javax.validation.Validator;

import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.JsonConfigurationFactory;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.configuration.provider.ConfigurationSourceProvider;
import io.dropwizard.configuration.provider.FileConfigurationSourceProvider;
import io.dropwizard.configuration.provider.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.provider.UTF8StringConfigurationSourceProvider;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.validation.BaseValidator;

public class Konfig<T extends Konfigurable> {

  public T buildConfigurationfromYAMLString(Class<T> klass, String configAsYAMLString)
      throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<T> factory =
        new YamlConfigurationFactory<>(klass, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new UTF8StringConfigurationSourceProvider();

    return factory.build(csp, configAsYAMLString);
  }

  public T buildConfigurationfromJSONString(Class<T> klass, String configAsYAMLString)
      throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<T> factory =
        new JsonConfigurationFactory<>(klass, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new UTF8StringConfigurationSourceProvider();

    return factory.build(csp, configAsYAMLString);
  }

  public T buildConfigurationfromYAMLFileAsResource(Class<T> klass, String resourceName)
      throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<T> factory =
        new YamlConfigurationFactory<>(klass, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new ResourceConfigurationSourceProvider();

    return factory.build(csp, resourceName);
  }

  public T buildConfigurationfromJSONFileAsResource(Class<T> klass, String resourceName)
      throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<T> factory =
        new JsonConfigurationFactory<>(klass, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new ResourceConfigurationSourceProvider();

    return factory.build(csp, resourceName);
  }

  public T buildConfigurationfromYAMLFilePath(Class<T> klass, String resourceName)
      throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<T> factory =
        new YamlConfigurationFactory<>(klass, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new FileConfigurationSourceProvider();

    return factory.build(csp, resourceName);
  }

  public T buildConfigurationfromJSONFilePath(Class<T> klass, String resourceName)
      throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<T> factory =
        new JsonConfigurationFactory<>(klass, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new FileConfigurationSourceProvider();

    return factory.build(csp, resourceName);
  }
}
