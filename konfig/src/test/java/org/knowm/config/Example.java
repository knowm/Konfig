package org.knowm.config;

import java.io.IOException;

import org.knowm.configuration.ConfigurationException;
import org.knowm.konfig.Konfig;

public class Example {

  public static void main(String[] args) throws IOException, ConfigurationException {

    Example example = new Example();
    example.goYAML();
    example.goYAML2();
    example.goJSON();
  }

  private void goYAML() throws IOException, ConfigurationException {

    ConfigurableObject configurableObject =
        new Konfig<ConfigurableObject>()
            .buildConfigurationfromYAMLFilePath(
                ConfigurableObject.class, "./src/test/resources/example.yml");

    System.out.println(configurableObject.toString());
  }

  private void goYAML2() throws IOException, ConfigurationException {

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

    ConfigurableObject configurableObject =
        new Konfig<ConfigurableObject>()
            .buildConfigurationfromYAMLString(ConfigurableObject.class, configAsYAMLString);
    System.out.println(configurableObject.toString());
  }

  private void goJSON() throws IOException, ConfigurationException {

    ConfigurableObject configurableObject =
        new Konfig<ConfigurableObject>()
            .buildConfigurationfromJSONFileAsResource(ConfigurableObject.class, "example.json");

    System.out.println(configurableObject.toString());
  }
}
