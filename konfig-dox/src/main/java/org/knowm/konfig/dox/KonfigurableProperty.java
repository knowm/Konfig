/**
 * Copyright 2017 Knowm Inc. (http://knowm.org) and contributors.
 *
 * This package also includes additional source code that is not
 * part of Konfig itself:
 *
 * * `Dropwizard-Configuration`: Copyright 2010-2013 Coda Hale and Yammer, Inc., 2014-2016 Dropwizard Team, Apache 2 License.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
