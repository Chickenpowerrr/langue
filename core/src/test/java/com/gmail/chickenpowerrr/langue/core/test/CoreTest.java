package com.gmail.chickenpowerrr.langue.core.test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderPattern;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceFactory;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdateManager;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdater;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.Test;

public class CoreTest {

  @Test
  public void addPluginTest() {
    assertTrue(new LanguageResourceFactory().getAvailableLanguageResources().contains("BasicResource"));
  }

  @Test
  public void getLanguageResourceTest() {
    assertNotNull(new LanguageResourceFactory().getLanguageResource("BasicResource"));
  }

  @Test
  public void getUniquePlaceholderPartTest() {
    assertEquals("test", new PlaceholderPattern("%%", "%%").getUniquePart("%%test%%"));
  }

  @Test
  public void replaceStaticPlaceholderTest() {
    assertEquals("John is the CEO",
        new LanguageResourceFactory().getLanguageResource(
            "BasicResource").getMessage("english", "ceo"));

    assertEquals("John is de CEO",
        new LanguageResourceFactory().getLanguageResource(
            "BasicResource").getMessage("nederlands", "ceo"));
  }

  @Test
  public void unknownPlaceholderTest() {
    assertEquals("Hello World", new LanguageResourceFactory()
        .getLanguageResource("BasicResource")
        .getMessage("english", "hello_world", new HashMap<String, String>() {{
          put("nothing", "something");
        }}));

    assertEquals("Hallo Wereld", new LanguageResourceFactory()
        .getLanguageResource("BasicResource")
        .getMessage("nederlands", "hello_world", new HashMap<String, String>() {{
          put("nothing", "something");
        }}));
  }

  @Test
  public void unknownLanguageTest() {
    assertNull(new LanguageResourceFactory().getLanguageResource("BasicResource")
        .getMessage("deutsch", "ceo"));
  }

  @Test
  public void unknownKeyTest() {
    assertNull(new LanguageResourceFactory().getLanguageResource("BasicResource")
        .getMessage("english", "non_existing"));
  }

  @Test
  public void replacePlaceholdersTest() {
    LanguageResource languageResource = new LanguageResourceFactory()
        .getLanguageResource("BasicResource");

    assertEquals("The answer is: Yes with 7 seconds to go",
        languageResource.getMessage("english", "answer", new HashMap<String, String>() {{
          put("seconds", Integer.toString(7));
        }}));

    assertEquals("Het antwoord is: Ja met nog 6 seconden te gaan",
        languageResource.getMessage("nederlands", "answer", new HashMap<String, String>() {{
          put("seconds", Integer.toString(6));
        }}));
  }

  @Test
  public void addLanguageTest() {
    LanguageResource languageResource = new LanguageResourceFactory()
        .getLanguageResource("BasicResource");
    LanguageResourceUpdateManager.getInstance().subscribe("Test", languageResource);
    assertNull(languageResource.getLanguage("deutsch"));
    assertNotNull(languageResource.getLanguage("nederlands"));
    assertNotNull(languageResource.getLanguage("english"));

    LanguageResourceUpdater updater = new LanguageResourceUpdater("Test");
    Map<String, Map<String, String>> languages = new HashMap<>();
    languages.put("deutsch", new HashMap<String, String>() {{
      put("hello_world", "Hallo Welt");
      put("bye_world", "Tschüss Welt");
    }});
    updater.addLanguages(languages);

    assertNotNull(languageResource.getLanguage("deutsch"));
    assertNotNull(languageResource.getLanguage("nederlands"));
    assertNotNull(languageResource.getLanguage("english"));
    assertEquals("Hallo Welt", languageResource.getMessage("deutsch", "hello_world"));
    assertEquals("Tschüss Welt", languageResource.getMessage("deutsch", "bye_world"));
  }

  @Test
  public void addTranslationsUpdateTest() {
    LanguageResource languageResource = new LanguageResourceFactory()
        .getLanguageResource("BasicResource");
    LanguageResourceUpdateManager.getInstance().subscribe("Test", languageResource);
    assertNull(languageResource.getLanguage("deutsch"));
    assertNotNull(languageResource.getLanguage("nederlands"));
    assertNotNull(languageResource.getLanguage("english"));

    LanguageResourceUpdater updater = new LanguageResourceUpdater("Test");
    Map<String, Map<String, String>> languages = new HashMap<>();
    languages.put("nederlands", new HashMap<String, String>() {{
      put("hello_there", "Hallo Daar");
      put("bye_there", "Doei Daar");
    }});
    updater.addTranslations(languages);

    assertNotNull(languageResource.getLanguage("nederlands"));
    assertNotNull(languageResource.getLanguage("english"));
    assertNull(languageResource.getLanguage("deutsch"));
    assertEquals("Hallo Daar", languageResource.getMessage("nederlands", "hello_there"));
    assertEquals("Doei Daar", languageResource.getMessage("nederlands", "bye_there"));
  }

  @Test
  public void deleteLanguagesUpdateTest() {
    LanguageResource languageResource = new BasicPlugin().getLanguageResource("BasicResource");
    LanguageResourceUpdateManager.getInstance().subscribe("Test", languageResource);
    assertNotNull(languageResource.getLanguage("nederlands"));
    assertNotNull(languageResource.getLanguage("english"));
    assertNull(languageResource.getLanguage("deutsch"));

    LanguageResourceUpdater updater = new LanguageResourceUpdater("Test");
    Collection<String> languages = new HashSet<String>() {{
      add("nederlands");
    }};
    updater.deleteLanguages(languages);

    assertNull(languageResource.getLanguage("nederlands"));
    assertNotNull(languageResource.getLanguage("english"));
    assertNull(languageResource.getLanguage("deutsch"));
    assertEquals("Hello World", languageResource.getMessage("english", "hello_world"));
  }

  @Test
  public void deleteTranslationsUpdateTest() {
    LanguageResource languageResource = new BasicPlugin().getLanguageResource("BasicResource");
    LanguageResourceUpdateManager.getInstance().subscribe("Test", languageResource);
    assertNotNull(languageResource.getMessage("english", "hello_world"));
    assertNotNull(languageResource.getMessage("nederlands", "hello_world"));

    LanguageResourceUpdater updater = new LanguageResourceUpdater("Test");
    Collection<String> translations = new HashSet<String>() {{
      add("hello_world");
    }};
    updater.deleteTranslations(translations);

    assertNotNull(languageResource.getLanguage("nederlands"));
    assertNotNull(languageResource.getLanguage("english"));
    assertNull(languageResource.getLanguage("deutsch"));
    assertNull(languageResource.getMessage("english", "hello_world"));
    assertNull(languageResource.getMessage("nederlands", "hello_world"));
  }
}
