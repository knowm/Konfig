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
