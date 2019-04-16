package com.gmail.chickenpowerrr.langue.mysql;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;

import java.util.function.Function;

/**
 * This class creates the connection between the MySqlDatabase and the implementation a
 * LanguageResource needs
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class MySqlLanguageResource extends LanguageResource {

  private final MySqlDatabase mySqlDatabase;

  /**
   * Creates a MySqlLanguageResource based on the way placeholders should get handled, messages
   * formatted and the credentials to login to the SQL server
   *
   * @param placeholderManager the way the placeholders should get handled
   * @param formatter the way messages should get formatted
   * @param credentials the credentials to login to the SQL server
   */
  public MySqlLanguageResource(PlaceholderManager placeholderManager,
      Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    super(placeholderManager, formatter, null);
    this.mySqlDatabase = new MySqlDatabase(credentials);

    super.languages = this.mySqlDatabase.getLanguages();
  }

  @Override
  public void reload() {
    super.languages = this.mySqlDatabase.getLanguages();
  }
}
