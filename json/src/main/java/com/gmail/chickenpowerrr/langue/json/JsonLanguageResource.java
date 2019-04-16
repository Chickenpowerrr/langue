package com.gmail.chickenpowerrr.langue.json;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;

import java.util.function.Function;

/**
 * This class creates the connection between the JsonFile and the implementation a LanguageResource
 * needs
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class JsonLanguageResource extends LanguageResource {

  private final JsonFile jsonFile;

  /**
   * Creates a JsonLanguageResource based on the way placeholders should get handled, messages
   * formatted and the path to the file
   *
   * @param placeholderManager the way the placeholders should get handled
   * @param formatter the way messages should get formatted
   * @param credentials contains the path to the Json file
   */
  public JsonLanguageResource(PlaceholderManager placeholderManager,
      Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    super(placeholderManager, formatter, null);
    this.jsonFile = new JsonFile(credentials);

    super.languages = this.jsonFile.getLanguages();
  }

  /**
   * Re-reads the Json file
   */
  @Override
  public void reload() {
    super.languages = this.jsonFile.getLanguages();
  }
}
