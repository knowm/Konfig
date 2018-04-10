package org.knowm.konfig;

import java.io.IOException;

import javax.validation.Validator;

import org.knowm.configuration.ConfigurationException;
import org.knowm.configuration.ConfigurationFactory;
import org.knowm.configuration.JsonConfigurationFactory;
import org.knowm.configuration.YamlConfigurationFactory;
import org.knowm.configuration.provider.ConfigurationSourceProvider;
import org.knowm.configuration.provider.FileConfigurationSourceProvider;
import org.knowm.configuration.provider.ResourceConfigurationSourceProvider;
import org.knowm.configuration.provider.UTF8StringConfigurationSourceProvider;
import org.knowm.jackson.Jackson;
import org.knowm.validation.BaseValidator;

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
