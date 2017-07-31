package io.dropwizard.jackson;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Issue1627 {
  private final String string;
  private final UUID uuid;

  public Issue1627(final String string, final UUID uuid) {
    this.string = string;
    this.uuid = uuid;
  }

  @JsonProperty
  public String getString() {
    return this.string;
  }

  @JsonProperty
  public UUID getUuid() {
    return this.uuid;
  }
}
