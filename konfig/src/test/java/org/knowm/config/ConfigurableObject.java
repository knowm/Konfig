package org.knowm.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.knowm.konfig.Konfigurable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurableObject implements Konfigurable {

  @NotNull
  @Pattern(regexp = "[\\w]+[\\s]+[\\w]+([\\s][\\w]+)?")
  private String name;

  @JsonProperty private int age = 1;

  List<String> type;

  @JsonProperty private Map<String, String> properties = new LinkedHashMap<>();

  @JsonProperty private List<ExampleServer> servers = new ArrayList<>();

  private boolean admin;

  @JsonProperty("my.logger")
  private Map<String, String> logger = new LinkedHashMap<>();

  public String getName() {
    return name;
  }

  public List<String> getType() {
    return type;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public List<ExampleServer> getServers() {
    return servers;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public Map<String, String> getLogger() {
    return logger;
  }

  @Override
  public String toString() {
    return "ConfigurableObject [name="
        + name
        + ", age="
        + age
        + ", type="
        + type
        + ", properties="
        + properties
        + ", servers="
        + servers
        + ", admin="
        + admin
        + ", logger="
        + logger
        + "]";
  }
}
