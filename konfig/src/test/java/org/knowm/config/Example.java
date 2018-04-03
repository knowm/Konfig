package org.knowm.config;

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

public class Example {

  public static void main(String[] args) throws IOException, ConfigurationException {

    Example example = new Example();
    example.goYAML();
    example.goYAML2();
    example.goJSON();
  }

  private void goYAML2() throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<ConfigurableObject> factory =
        new YamlConfigurationFactory<>(
            ConfigurableObject.class, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new UTF8StringConfigurationSourceProvider();

    String configAsYAMLString =
        "name: Coda Hale\n"
            + "type:\n"
            + "    - coder\n"
            + "    - wizard\n"
            + "properties:\n"
            + "  debug: true\n"
            + "  settings.enabled: false\n"
            + "servers:\n"
            + "  - port: 8080\n"
            + "  - port: 8081\n"
            + "  - port: 8082\n"
            + "my.logger:\n"
            + "  level: info\n"
            + "";

    ConfigurableObject configurableObject = factory.build(csp, configAsYAMLString);
    System.out.println(configurableObject.toString());
  }

  private void goYAML() throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<ConfigurableObject> factory =
        new YamlConfigurationFactory<>(
            ConfigurableObject.class, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new FileConfigurationSourceProvider();

    ConfigurableObject configurableObject = factory.build(csp, "./src/test/resources/example.yml");
    System.out.println(configurableObject.toString());
  }

  private void goJSON() throws IOException, ConfigurationException {

    Validator validator = BaseValidator.newValidator();

    ConfigurationFactory<ConfigurableObject> factory =
        new JsonConfigurationFactory<>(
            ConfigurableObject.class, validator, Jackson.newObjectMapper(), "k");

    ConfigurationSourceProvider csp = new ResourceConfigurationSourceProvider();

    ConfigurableObject configurableObject = factory.build(csp, "example.json");
    System.out.println(configurableObject.toString());
  }
}
