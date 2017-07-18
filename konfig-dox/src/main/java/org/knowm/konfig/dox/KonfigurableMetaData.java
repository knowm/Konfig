package org.knowm.konfig.dox;


import java.util.ArrayList;
import java.util.List;

public class KonfigurableMetaData {

  private String className = "";
  private String description = "";
  private List<KonfigurableProperty> properties = new ArrayList<>();

  public String getClassName() {

    return className;
  }

  public void setClassName(String className) {

    this.className = className;
  }

  public String getDescription() {

    return description;
  }

  public void setDescription(String description) {

    this.description = description;
  }


  public List<KonfigurableProperty> getProperties() {

    return properties;
  }

  public void setProperties(List<KonfigurableProperty> properties) {

    this.properties = properties;
  }

}
