package org.knowm.konfig.dox;



public class KonfigurableProperty {

  String propertyName = "";
  String description = "TODO - missing property JavaDoc!";
  String defaultValue = "";
  String minValue = "";
  String maxValue = "";
  boolean required = false;

  public String getPropertyName() {

    return propertyName;
  }

  public void setPropertyName(String propertyName) {

    this.propertyName = propertyName;
  }

  public String getDescription() {

    return description;
  }

  public void setDescription(String description) {

    this.description = description;
  }

  public String getDefaultValue() {

    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {

    this.defaultValue = defaultValue;
  }

  public String getMinValue() {

    return minValue;
  }

  public void setMinValue(String minValue) {

    this.minValue = minValue;
  }

  public String getMaxValue() {

    return maxValue;
  }

  public void setMaxValue(String maxValue) {

    this.maxValue = maxValue;
  }

  public boolean isRequired() {

    return required;
  }

  public void setRequired(boolean isRequired) {

    this.required = isRequired;
  }

  @Override
  public String toString() {

    return "KonfigurableProperty [propertyName=" + propertyName + ", description=" + description + ", defaultValue=" + defaultValue
        + ", minValue=" + minValue + ", maxValue=" + maxValue + ", isRequired=" + required + "]";
  }

}
