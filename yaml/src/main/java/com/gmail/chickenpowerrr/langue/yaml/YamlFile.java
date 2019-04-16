package com.gmail.chickenpowerrr.langue.yaml;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * This class contains all of the methods needed to read a basic Yaml file that contains the
 * relevant language information
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class YamlFile {

  private final File file;
  private final Yaml yaml = new Yaml();

  /**
   * Reads the file that contains all of the language information
   *
   * @param credentials the file that contains the language information
   */
  public YamlFile(LanguageResourceCredentials credentials) {
    this.file = new File(credentials.getString("fileLocation"));
  }

  /**
   * Reads all of the available languages from the Yaml file
   *
   * @return all of the available languages
   */
  public Map<String, ResourceLanguage> getLanguages() {
    try (InputStream inputStream = new FileInputStream(file)) {
      Map<String, Map<String, String>> objects = this.yaml.load(inputStream);
      return objects.entrySet().stream().map(entry -> new HashMap.SimpleEntry<>(entry.getKey(),
          new ResourceLanguage(entry.getKey(), entry.getValue())))
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
