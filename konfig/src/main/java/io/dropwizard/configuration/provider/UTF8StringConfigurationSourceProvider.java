package io.dropwizard.configuration.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * An implementation of {@link ConfigurationSourceProvider} that reads the configuration from a
 * UTF-8-encoded String
 */
public class UTF8StringConfigurationSourceProvider implements ConfigurationSourceProvider {
  @Override
  public InputStream open(String configAsString) throws IOException {

    return new ByteArrayInputStream(configAsString.getBytes(StandardCharsets.UTF_8));
  }
}
