/**
 * Copyright 2017 Knowm Inc. (http://knowm.org) and contributors.
 *
 * <p>This package also includes additional source code that is not part of Konfig itself:
 *
 * <p>* `Dropwizard-Configuration`: Copyright 2010-2013 Coda Hale and Yammer, Inc., 2014-2016
 * Dropwizard Team, Apache 2 License.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.knowm.konfig.dox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaSource;

public class QDoxUtil {

  /**
   * Get the konfigurable class names in a given package
   *
   * @param packageName
   * @param konfigurable
   * @return
   */
  public static List<String> getDiscoverableClassNames(String packageName, String konfigurable) {

    JavaProjectBuilder builder = new JavaProjectBuilder();
    builder.addSourceTree(new File(packageName));

    // list to return
    List<String> konfigurableClasses = new ArrayList<>();

    // for each source file
    for (JavaSource src : builder.getSources()) {

      // get the first class defined in the source file (assuming only one per file)
      //      JavaClass originalClass = src.getClasses().get(0);
      JavaClass originalClass =
          builder.getClassByName(src.getClasses().get(0).getFullyQualifiedName());

      // skip if abstract class
      if (originalClass.isAbstract()) {
        continue;
      }
      JavaClass cls = src.getClasses().get(0);
      boolean isKonfigurable = false;

      // crawl up the class hierarchy and see if it extends "Konfigurable"
      do {
        //        System.out.println("***" + cls.getCanonicalName());
        //        System.out.println("---" + cls.getParentSource().getClasses().get(0));
        //        System.out.println("---" + cls.isAbstract());
        //        System.out.println("---" + cls.getSuperJavaClass());
        //        System.out.println("---" + cls.getImplements());

        // if we went all the way up to java.lang.Object, the super class will be null
        if (cls.getSuperJavaClass() == null) {
          break;
        }

        for (JavaClass javaClass : cls.getInterfaces()) {
          if (javaClass.getName().equals(konfigurable)) {
            isKonfigurable = true;
            konfigurableClasses.add(originalClass.getCanonicalName());
            break;
          }
        }

        //        if (cls.getSuperJavaClass().isA(konfigurable)) {
        //          isKonfigurable = true;
        //          discoverableClasses.add(originalClass.getCanonicalName());
        //          break;
        //        }

        cls = cls.getSuperJavaClass();
      } while (cls != null && !isKonfigurable);
    }
    return konfigurableClasses;
  }

  /**
   * For each property in the Konfigurable class, create a KonfigurableProperty object
   *
   * @param packageName
   * @param discoverableClasses
   * @return a map with the class name as key and a List of Konfigurable as value
   */
  public static List<KonfigurableMetaData> generateModel(
      String packageName, List<String> discoverableClasses) {

    JavaProjectBuilder builder = new JavaProjectBuilder();
    builder.addSourceTree(new File(packageName));

    List<KonfigurableMetaData> konfigurableMetaDataList = new ArrayList<>();

    //    Collection<JavaSource> sources = builder.getSources();
    // System.out.println(sources.size());

    // for each source file
    for (JavaSource src : builder.getSources()) {

      List<KonfigurableProperty> discoverableProperties = new ArrayList<>();

      // get the first class defined in the source file (assuming only one per file)
      JavaClass originalClass = src.getClasses().get(0);

      // skip if we know it's not discoverable
      if (!discoverableClasses.contains(originalClass.getCanonicalName())) {
        continue;
      }

      // 1. Set the class name
      KonfigurableMetaData konfigurableMetaData = new KonfigurableMetaData();
      konfigurableMetaData.setClassName(originalClass.getCanonicalName());

      // 2. set the class description
      String description =
          originalClass.getComment() == null
              ? "TODO - missing class JavaDoc!"
              : originalClass.getComment();
      konfigurableMetaData.setDescription(description);

      JavaClass cls = src.getClasses().get(0);

      do {
        // System.out.println("***" + cls.getCanonicalName());
        // System.out.println("---" + cls.getParentSource().getClasses().get(0));
        // System.out.println("---" + cls.getSuperJavaClass());

        // for each field, see it it's JsonProperty annotated
        for (JavaField javaField : cls.getFields()) {

          boolean isJsonProperty = false;
          List<JavaAnnotation> fieldAnnotations = javaField.getAnnotations();
          for (JavaAnnotation javaAnnotation : fieldAnnotations) {
            if (javaAnnotation
                .getType()
                .getCanonicalName()
                .equals("com.fasterxml.jackson.annotation.JsonProperty")) {
              isJsonProperty = true;
              break;
            }
          }
          if (isJsonProperty) {

            // create new DTO
            KonfigurableProperty konfigurableProperty = new KonfigurableProperty();

            // set the property's name
            konfigurableProperty.setPropertyName(javaField.getName());

            // set it's initial value if it has one
            if (javaField.getInitializationExpression() != null) {
              konfigurableProperty.setDefaultValue(javaField.getInitializationExpression());
            }
            // set it's description if it has one
            if (javaField.getComment() != null) {
              konfigurableProperty.setDescription(javaField.getComment());
            }

            // set certain annotation values if they exist
            fieldAnnotations = javaField.getAnnotations();
            for (JavaAnnotation javaAnnotation : fieldAnnotations) {
              // System.out.println(javaAnnotation.getType());
              if (javaAnnotation
                  .getType()
                  .getCanonicalName()
                  .equals("javax.validation.constraints.NotNull")) {
                konfigurableProperty.setRequired(true);
              }
              if (javaAnnotation
                  .getType()
                  .getCanonicalName()
                  .equals("javax.validation.constraints.Min")) {
                konfigurableProperty.setMinValue(javaAnnotation.getProperty("value").toString());
              }
              if (javaAnnotation
                  .getType()
                  .getCanonicalName()
                  .equals("javax.validation.constraints.Max")) {
                konfigurableProperty.setMaxValue(javaAnnotation.getProperty("value").toString());
              }
            }
            discoverableProperties.add(konfigurableProperty);
          }
          // System.out.println("---" + javaField.getName() + " : " +
          // Arrays.toString(javaField.getAnnotations().toArray()));

        }

        cls = cls.getSuperJavaClass();
      } while (cls != null);

      konfigurableMetaData.setProperties(discoverableProperties);
      konfigurableMetaDataList.add(konfigurableMetaData);
    }
    return konfigurableMetaDataList;
  }
}
