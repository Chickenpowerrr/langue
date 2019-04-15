package com.gmail.chickenpowerrr.langue.core.update;

import com.gmail.chickenpowerrr.autoloader.AutoLoaderApi;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;

import java.util.HashMap;
import java.util.Map;

public class LanguageResourceUpdaterFactory {

  private final Map<String, LanguePlugin> languageResourceUpdaters = new HashMap<>();

  public LanguageResourceUpdaterFactory() {
    for (LanguePlugin plugin : AutoLoaderApi.getApi()
        .loadClasses(LanguePlugin.class, "com.gmail.chickenpowerrr.langue", LanguePlugin.class)) {
      for (String languageResource : plugin.getAvailableLanguageResourceUpdaters()) {
        this.languageResourceUpdaters.put(languageResource, plugin);
      }
    }
  }

  public LanguageResourceUpdater getLanguageResourceUpdater(String name,
      LanguageResourceCredentials credentials, String... channels) {
    return this.languageResourceUpdaters.get(name).getLanguageResourceUpdater(name, credentials);
  }

  public final LanguageResourceUpdater getLanguageResourceUpdater(String name, String... channels) {
    return getLanguageResourceUpdater(name, null, channels);
  }
}
