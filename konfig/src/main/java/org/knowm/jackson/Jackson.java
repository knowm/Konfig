package org.knowm.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * A utility class for Jackson.
 */
public class Jackson {
  private Jackson() { /* singleton */ }

  /**
   * Creates a new {@link ObjectMapper} with Guava, Logback, and Joda Time support, as well as
   * support for {@link JsonSnakeCase}. Also includes all {@link Discoverable} interface implementations.
   */
  public static ObjectMapper newObjectMapper() {
    final ObjectMapper mapper = new ObjectMapper();

    return configure(mapper);
  }

  /**
   * Creates a new {@link ObjectMapper} with a custom {@link com.fasterxml.jackson.core.JsonFactory}
   * with Guava, Logback, and Joda Time support, as well as support for {@link JsonSnakeCase}.
   * Also includes all {@link Discoverable} interface implementations.
   *
   * @param jsonFactory instance of {@link com.fasterxml.jackson.core.JsonFactory} to use
   * for the created {@link com.fasterxml.jackson.databind.ObjectMapper} instance.
   */
  public static ObjectMapper newObjectMapper(JsonFactory jsonFactory) {
    final ObjectMapper mapper = new ObjectMapper(jsonFactory);

    return configure(mapper);
  }

  /**
   * Creates a new minimal {@link ObjectMapper} that will work with Dropwizard out of box.
   * <p><b>NOTE:</b> Use it, if the default Dropwizard's {@link ObjectMapper}, created in
   * {@link #newObjectMapper()}, is too aggressive for you.</p>
   */
  public static ObjectMapper newMinimalObjectMapper() {
    return new ObjectMapper();
  }

  private static ObjectMapper configure(ObjectMapper mapper) {
    mapper.registerModule(new AfterburnerModule());
    mapper.registerModule(new FuzzyEnumModule());
    mapper.registerModule(new ParameterNamesModule());
    mapper.registerModules(new Jdk8Module());
    mapper.registerModules(new JavaTimeModule());
    mapper.setPropertyNamingStrategy(new AnnotationSensitivePropertyNamingStrategy());

    return mapper;
  }
}
