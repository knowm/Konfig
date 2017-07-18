## Konfig

Konfig is a tool to deserialize JSON/YAML configuration files, while additionally validating and auto-generating documentation.

## Description

Konfig is a fork of `dropwizard-configuration`. Konfig removes a few dependencies including Guava and does away with the need to add certain files
in `META_INF`. Konfig allows you to serialize and deserialize configuration objects to either YAML or JSON from annotated Java classes. While
deserializing, Konfig validates each property's given value if an approprate annotation is included (`@Min`, `@Max`, etc.). Konfig-dox is a Maven
 plugin that auto-generates documentation for your configuration classes based off the annotations and class and property JavaDocs. Given an array
 of package names to search, it will find all classes implementing `Konfigurable` and generate the documentation in JSON and HTML form.

## Example

See [JSpice](https://github.com/knowm/jspice).

## TODO

1. Better documentation with examples
1. Test JSON deserialization
