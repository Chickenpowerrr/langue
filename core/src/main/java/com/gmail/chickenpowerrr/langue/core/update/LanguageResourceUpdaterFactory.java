package com.gmail.chickenpowerrr.langue.core.update;

import com.gmail.chickenpowerrr.autoloader.AutoLoaderApi;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;

import java.util.HashMap;
import java.util.Map;

/**
 * This class combines all LanguageResourceUpdaters to create a central access point
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class LanguageResourceUpdaterFactory {

  private final Map<String, LanguePlugin> languageResourceUpdaters = new HashMap<>();

  /**
   * Finds all LanguageResourceUpdaters and saves all available LanguageResourceUpdaters
   */
  public LanguageResourceUpdaterFactory() {
    for (LanguePlugin plugin : AutoLoaderApi.getApi()
        .loadClasses(LanguePlugin.class, "com.gmail.chickenpowerrr.langue", LanguePlugin.class)) {
      for (String languageResource : plugin.getAvailableLanguageResourceUpdaters()) {
        this.languageResourceUpdaters.put(languageResource, plugin);
      }
    }
  }

  /**
   * Returns a LanguageResourceUpdaters based on its name, subscribed channels and the credentials used to connect to the updater
   *
   * @param name the name of the LanguageResourceUpdater
   * @param credentials the credentials used to login
   * @param channels the channels that should get notified on an update
   * @return the requested LanguageResourceUpdater
   */
  public LanguageResourceUpdater getLanguageResourceUpdater(String name,
      LanguageResourceCredentials credentials, String... channels) {
    return this.languageResourceUpdaters.get(name).getLanguageResourceUpdater(name, credentials);
  }

  /**
   * Returns a LanguageResourceUpdaters based on its name and the subscribed channels
   *
   * @param name the name of the LanguageResourceUpdater
   * @param channels the channels that should get notified on an update
   * @return the requested LanguageResourceUpdater
   */
  public final LanguageResourceUpdater getLanguageResourceUpdater(String name, String... channels) {
    return getLanguageResourceUpdater(name, null, channels);
  }
}
