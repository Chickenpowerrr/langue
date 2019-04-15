package com.gmail.chickenpowerrr.langue.pool.test;

import static org.junit.Assert.assertEquals;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceFactory;
import java.io.File;
import java.util.HashMap;
import org.junit.Test;

public class PoolTest {

  private final LanguageResourceFactory languageResourceFactory = new LanguageResourceFactory();

  @Test
  public void multipleResourcesTest() {
    assertEquals("Hello World", getLanguageResourcePool().getMessage(
        "english", "yml1.hello_world"));
    assertEquals("Hello World!", getLanguageResourcePool().getMessage(
        "english", "yml2.hello_world"));
    assertEquals("Hallo Wereld", getLanguageResourcePool().getMessage(
        "nederlands", "yml1.hello_world"));
    assertEquals("Hallo Wereld!", getLanguageResourcePool().getMessage(
        "nederlands", "yml2.hello_world"));
  }

  @Test
  public void singleTranslationTest() {
    assertEquals("ERROR!", getLanguageResourcePool().getMessage("english", "error"));
    assertEquals("FOUT!", getLanguageResourcePool().getMessage("nederlands", "error"));
  }

  @Test
  public void placeholderTest() {
    assertEquals("Red is a color", getLanguageResourcePool().getMessage(
        "english", "color", new HashMap<String, String>() {{
          put("color", "Red");
        }}));
    assertEquals("Rood is een kleur", getLanguageResourcePool().getMessage(
        "nederlands", "color", new HashMap<String, String>() {{
          put("color", "Rood");
        }}));
  }

  @Test
  public void allPlaceholderTest() {
    assertEquals("Hello World: Red 4 %%nothing%%", getLanguageResourcePool().getMessage(
        "english", "random", new HashMap<String, String>() {{
          put("value", "4");
        }}));
    assertEquals("Hallo Wereld: Rood 4 %%nothing%%", getLanguageResourcePool().getMessage(
        "nederlands", "random", new HashMap<String, String>() {{
          put("value", "4");
        }}));
  }

  private LanguageResource getLanguageResourcePool() {
    return this.languageResourceFactory
        .getLanguageResource("Pool", new LanguageResourceCredentials()
            .addProperty("parts", new HashMap<String, LanguageResource>() {{
              put("yml1", languageResourceFactory
                  .getLanguageResource("Yaml", new LanguageResourceCredentials()
                      .addProperty("fileLocation", new File("resources").getAbsolutePath()
                          .replace("resources", "src/main/resources/test.yml"))));
              put("yml2", languageResourceFactory
                  .getLanguageResource("Yaml", new LanguageResourceCredentials()
                      .addProperty("fileLocation", new File("resources").getAbsolutePath()
                          .replace("resources", "src/main/resources/test2.yml"))));
              put("json", languageResourceFactory
                  .getLanguageResource("JSON", new LanguageResourceCredentials()
                      .addProperty("fileLocation", new File("resources").getAbsolutePath()
                          .replace("resources", "src/main/resources/test.json"))));
            }}));
  }
}
