package com.gmail.chickenpowerrr.langue.yaml.test;

import static org.junit.Assert.assertEquals;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceFactory;
import java.io.File;
import org.junit.Test;

public class YamlTest {

  private final LanguageResourceCredentials credentials = new LanguageResourceCredentials()
      .addProperty(
          "fileLocation", new File("resources").getAbsolutePath()
              .replace("resources", "src/main/resources/test.yml"));

  @Test
  public void loadFileTest() {
    new LanguageResourceFactory().getLanguageResource("Yaml", this.credentials);
  }

  @Test
  public void loadMessageTest() {
    assertEquals("Hallo Wereld", new LanguageResourceFactory().getLanguageResource(
        "Yaml", this.credentials).getMessage("nederlands", "hello_world"));
    assertEquals("Hello World", new LanguageResourceFactory().getLanguageResource(
        "Yaml", this.credentials).getMessage("english", "hello_world"));
  }
}
