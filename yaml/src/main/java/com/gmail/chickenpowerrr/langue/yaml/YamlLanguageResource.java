package com.gmail.chickenpowerrr.langue.yaml;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;

import java.util.function.Function;

/**
 * This class creates the connection between the YamlFile and the implementation a LanguageResource
 * needs
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class YamlLanguageResource extends LanguageResource {

  private final YamlFile yamlFile;

  /**
   * Creates a YamlLanguageResource based on the way placeholders should get handled, messages
   * formatted and the path to the file
   *
   * @param placeholderManager the way the placeholders should get handled
   * @param formatter the way messages should get formatted
   * @param credentials contains the path to the Yaml file
   */
  public YamlLanguageResource(PlaceholderManager placeholderManager,
      Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    super(placeholderManager, formatter, null);
    this.yamlFile = new YamlFile(credentials);

    super.languages = this.yamlFile.getLanguages();
  }

  @Override
  public void reload() {
    super.languages = this.yamlFile.getLanguages();
  }
}
