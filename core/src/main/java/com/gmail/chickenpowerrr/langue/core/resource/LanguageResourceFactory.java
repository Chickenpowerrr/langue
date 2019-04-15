package com.gmail.chickenpowerrr.langue.core.resource;

import com.gmail.chickenpowerrr.autoloader.AutoLoaderApi;
import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LanguageResourceFactory {

  private final Map<String, LanguePlugin> languageResources = new HashMap<>();

  public LanguageResourceFactory() {
    for (LanguePlugin plugin : AutoLoaderApi.getApi()
        .loadClasses(LanguePlugin.class, "com.gmail.chickenpowerrr.langue", LanguePlugin.class)) {
      for (String languageResource : plugin.getAvailableLanguageResources()) {
        this.languageResources.put(languageResource, plugin);
      }
    }
  }

  public Collection<String> getAvailableLanguages() {
    return this.languageResources.keySet();
  }

  public LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
      Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    return this.languageResources.get(name)
        .getLanguageResource(name, placeholderManager, formatter, credentials);
  }

  public final LanguageResource getLanguageResource(String name, Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    return getLanguageResource(name, null, formatter, credentials);
  }

  public final LanguageResource getLanguageResource(String name,
      PlaceholderManager placeholderManager,
      LanguageResourceCredentials credentials) {
    return getLanguageResource(name, placeholderManager, null, credentials);
  }

  public final LanguageResource getLanguageResource(String name,
      PlaceholderManager placeholderManager,
      Function<String, String> formatter) {
    return getLanguageResource(name, placeholderManager, formatter, null);
  }

  public final LanguageResource getLanguageResource(String name,
      LanguageResourceCredentials credentials) {
    return getLanguageResource(name, null, null, credentials);
  }

  public final LanguageResource getLanguageResource(String name,
      Function<String, String> formatter) {
    return getLanguageResource(name, null, formatter, null);
  }

  public final LanguageResource getLanguageResource(String name,
      PlaceholderManager placeholderManager) {
    return getLanguageResource(name, placeholderManager, null, null);
  }

  public final LanguageResource getLanguageResource(String name) {
    return getLanguageResource(name, null, null, null);
  }
}
