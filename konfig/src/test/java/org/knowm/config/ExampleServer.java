package org.knowm.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExampleServer {

  @JsonProperty private int port = 8000;

  public int getPort() {
    return port;
  }

  @Override
  public String toString() {
    return "ExampleServer [port=" + port + "]";
  }
}
