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
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/** @phase process-sources */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class KonfigDoxMojo extends AbstractMojo {

  /** Location of source code */
  @Parameter(defaultValue = "${project.basedir}/src/main/java", readonly = true, required = true)
  private File sourcesDirectory;

  /** Location of resources */
  @Parameter(
    defaultValue = "${project.basedir}/src/main/resources",
    readonly = true,
    required = true
  )
  private File resourcesDirectory;

  /** Class to include in search */
  @Parameter(defaultValue = "org.knowm.konfig.Konfigurable", readonly = true, required = true)
  private String konfigurable;

  /** A set of packages to look for Konfigurables */
  @Parameter(property = "packages", readonly = true, required = true)
  private String[] packages;

  @Override
  public void execute() throws MojoExecutionException {

    getLog().info("Generating Konfig Dox...");

    List<KonfigurableMetaData> konfigurableMetaData = new ArrayList<>();

    for (int i = 0; i < packages.length; i++) {

      String converted2Path =
          sourcesDirectory.getPath() + File.separatorChar + packages[i].replace(".", "/");

      getLog().info("Searching package: " + converted2Path + " for " + konfigurable + "s...");

      List<String> konfigurableClasses =
          QDoxUtil.getDiscoverableClassNames(converted2Path, konfigurable);

      getLog().info("konfigurableClasses = " + konfigurableClasses);

      konfigurableMetaData.addAll(QDoxUtil.generateModel(converted2Path, konfigurableClasses));
    }
    renderModelInConsole(konfigurableMetaData);
    renderModelInJSON(konfigurableMetaData, "Konfig");
    renderModelWithFreemarker(konfigurableMetaData, "Konfig");
    getLog().info("Generating Konfig Dox Finished.");
  }

  private void renderModelInConsole(List<KonfigurableMetaData> model) {

    for (KonfigurableMetaData entry : model) {

      String className = entry.getClassName();
      getLog().info(className);
      getLog().info("--------------------------------------------------------");
      getLog().info(entry.getDescription());

      for (KonfigurableProperty discoverableProperty : entry.getProperties()) {
        getLog().info(discoverableProperty.toString());
      }
      getLog().info("-");
    }
  }

  private void renderModelInJSON(List<KonfigurableMetaData> model, String metaInfFileName) {

    try {
      ObjectMapper mapper = new ObjectMapper();
      String modelAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
      Writer fileWriter =
          new FileWriter(
              new File(
                  resourcesDirectory.getPath()
                      + File.separatorChar
                      + metaInfFileName.substring(metaInfFileName.lastIndexOf(".") + 1)
                      + ".json"));
      fileWriter.write(modelAsString);
      fileWriter.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void renderModelWithFreemarker(List<KonfigurableMetaData> model, String metaInfFileName) {

    try {

      // 1. Configure FreeMarker

      Configuration cfg = new Configuration(new Version(2, 3, 20));

      // Where do we load the templates from:
      cfg.setClassForTemplateLoading(this.getClass(), "/");

      // Some other recommended settings:
      cfg.setDefaultEncoding("UTF-8");
      cfg.setLocale(Locale.US);
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

      // 2. Proccess template(s)

      // 2.1. Prepare the template input:

      Map<String, Object> input = new HashMap<String, Object>();

      input.put("model", model);

      // 2.2. Get the template
      Template template = cfg.getTemplate("konfigurations.ftl");

      // 2.3. Generate the output

      // Write output to the console
      // Writer consoleWriter = new OutputStreamWriter(System.out);
      // template.process(input, consoleWriter);

      // write output into a file:
      Writer fileWriter =
          new FileWriter(
              new File(
                  resourcesDirectory.getPath()
                      + File.separatorChar
                      + metaInfFileName.substring(metaInfFileName.lastIndexOf(".") + 1)
                      + ".html"));
      try {
        template.process(input, fileWriter);
      } finally {
        fileWriter.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
