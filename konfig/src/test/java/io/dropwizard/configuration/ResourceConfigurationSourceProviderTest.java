package io.dropwizard.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

import io.dropwizard.configuration.provider.ConfigurationSourceProvider;
import io.dropwizard.configuration.provider.ResourceConfigurationSourceProvider;

public class ResourceConfigurationSourceProviderTest {
  private final ConfigurationSourceProvider provider = new ResourceConfigurationSourceProvider();

  @Test
  public void readsFileContents() throws Exception {
    assertForWheeContent("example.txt");
    assertForWheeContent("io/dropwizard/configuration/not-root-example.txt");
    assertForWheeContent("/io/dropwizard/configuration/not-root-example.txt");
  }

  private void assertForWheeContent(String path) throws Exception {
    assertThat(loadResourceAsString(path)).isEqualTo("whee");
  }

  private String loadResourceAsString(String path) throws Exception {
    try (InputStream input = provider.open(path)) {

      Scanner s = new Scanner(input).useDelimiter("\\A");
      return s.hasNext() ? s.next().replace("\n", "") : "";

    }
  }
}
