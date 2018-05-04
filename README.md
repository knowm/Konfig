## Konfig

Konfig is a tool to deserialize JSON/YAML configuration files, while additionally validating and auto-generating documentation.

## Description

Konfig is a fork of `dropwizard-configuration`. Konfig removes a few dependencies including Guava and Logback and does away with the need to add
certain files in `META_INF`. Konfig allows you to serialize and deserialize configuration objects to either YAML or JSON from annotated Java
classes. While deserializing, Konfig validates each property's given value if an appropriate annotation is included (`@Min`, `@Max`, etc.).

Konfig-dox is a Maven  plugin that auto-generates documentation for your configuration classes based off the annotations and class and property
JavaDocs. Given an array of package names to search, it will find all classes implementing `Konfigurable` and generate the documentation in JSON
and HTML form.


## Example.java

```java
  private void goJSON() throws IOException, ConfigurationException {

    ConfigurableObject configurableObject =
        new Konfig<ConfigurableObject>()
            .buildConfigurationfromJSONFileAsResource(ConfigurableObject.class, "example.json");

    System.out.println(configurableObject.toString());
  }
```

## example.json

```json
{
    "name": "Coda Hale",
    "type": [
        "coder",
        "wizard"
    ],
    "properties": {
        "debug": true,
        "settings.enabled": false
    },
    "servers": [
        {
            "port": 8080
        },
        {
            "port": 8081
        },
        {
            "port": 8082
        }
    ],
    "my.logger": {
        "level": "info"
    }
}
```

## ConfigurableObject.java 

```java
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
```

## Output

```
ConfigurableObject [name=Coda Hale, age=1, type=[coder, wizard], properties={debug=true, settings.enabled=false}, servers=[ExampleServer [port=8080], ExampleServer [port=8081], ExampleServer [port=8082]], admin=false, logger={level=info}]
```

## Extra Options

1. You can override value with a Java Vm arg like this: `-Dk.name="Tim Molter"`.
1. Annotations can be use to constrain fields: min, max, etc.
1. You can load config files as Strings, from resources and as a File.

## Building

Konfig is built with Maven, which also handles dependency management.

### general

    cd path/to/project
    mvn clean package
    mvn javadoc:aggregate


