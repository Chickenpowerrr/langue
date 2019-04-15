package com.gmail.chickenpowerrr.langue.mysql;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;

import java.util.function.Function;

public class MySqlLanguageResource extends LanguageResource {

  private final MySqlDatabase mySqlDatabase;

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
