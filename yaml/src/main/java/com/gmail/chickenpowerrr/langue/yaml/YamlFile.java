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

public class YamlFile {

  private final File file;
  private final Yaml yaml = new Yaml();

  public YamlFile(LanguageResourceCredentials credentials) {
    this.file = new File(credentials.getString("fileLocation"));
  }

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
