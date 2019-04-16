package com.gmail.chickenpowerrr.langue.core.resource;

import com.gmail.chickenpowerrr.autoloader.AutoLoaderApi;
import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class combines all LanguePlugins to create a central access point
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class LanguageResourceFactory {

  private final Map<String, LanguePlugin> languageResources = new HashMap<>();

  /**
   * Finds all LanguePlugins and saves all available LanguageResources
   */
  public LanguageResourceFactory() {
    for (LanguePlugin plugin : AutoLoaderApi.getApi()
        .loadClasses(LanguePlugin.class, "com.gmail.chickenpowerrr.langue", LanguePlugin.class)) {
      for (String languageResource : plugin.getAvailableLanguageResources()) {
        this.languageResources.put(languageResource, plugin);
      }
    }
  }

  /**
   * Returns all available LanguageResources
   */
  public Collection<String> getAvailableLanguageResources() {
    return this.languageResources.keySet();
  }

  /**
   * Returns a LanguageResource based on the name, the format of the placeholders, the way a message
   * should get formatted and the credentials for the resource
   *
   * @param name the name of the LanguageResource
   * @param placeholderManager the way placeholders should get handled
   * @param formatter the way the message should get formatted
   * @param credentials the credentials needed to login to the resource
   * @return the LanguageResource that was requested
   */
  public LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
      Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    return this.languageResources.get(name)
        .getLanguageResource(name, placeholderManager, formatter, credentials);
  }

  /**
   * Returns a LanguageResource based on the name, the way a message should get formatted and the
   * credentials for the resource
   *
   * @param name the name of the LanguageResource
   * @param formatter the way the message should get formatted
   * @param credentials the credentials needed to login to the resource
   * @return the LanguageResource that was requested
   */
  public final LanguageResource getLanguageResource(String name, Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    return getLanguageResource(name, null, formatter, credentials);
  }

  /**
   * Returns a LanguageResource based on the name, the format of the placeholders and the
   * credentials for the resource
   *
   * @param name the name of the LanguageResource
   * @param placeholderManager the way placeholders should get handled
   * @param credentials the credentials needed to login to the resource
   * @return the LanguageResource that was requested
   */
  public final LanguageResource getLanguageResource(String name,
      PlaceholderManager placeholderManager,
      LanguageResourceCredentials credentials) {
    return getLanguageResource(name, placeholderManager, null, credentials);
  }

  /**
   * Returns a LanguageResource based on the name, the format of the placeholders and the way a
   * message should get formatted
   *
   * @param name the name of the LanguageResource
   * @param placeholderManager the way placeholders should get handled
   * @param formatter the way the message should get formatted
   * @return the LanguageResource that was requested
   */
  public final LanguageResource getLanguageResource(String name,
      PlaceholderManager placeholderManager,
      Function<String, String> formatter) {
    return getLanguageResource(name, placeholderManager, formatter, null);
  }

  /**
   * Returns a LanguageResource based on the name and the credentials for the resource
   *
   * @param name the name of the LanguageResource
   * @param credentials the credentials needed to login to the resource
   * @return the LanguageResource that was requested
   */
  public final LanguageResource getLanguageResource(String name,
      LanguageResourceCredentials credentials) {
    return getLanguageResource(name, null, null, credentials);
  }

  /**
   * Returns a LanguageResource based on the name and the way a message should get formatted
   *
   * @param name the name of the LanguageResource
   * @param formatter the way the message should get formatted
   * @return the LanguageResource that was requested
   */
  public final LanguageResource getLanguageResource(String name,
      Function<String, String> formatter) {
    return getLanguageResource(name, null, formatter, null);
  }

  /**
   * Returns a LanguageResource based on the name and the format of the placeholders
   *
   * @param name the name of the LanguageResource
   * @param placeholderManager the way placeholders should get handled
   * @return the LanguageResource that was requested
   */
  public final LanguageResource getLanguageResource(String name,
      PlaceholderManager placeholderManager) {
    return getLanguageResource(name, placeholderManager, null, null);
  }

  /**
   * Returns a LanguageResource based on the name
   *
   * @param name the name of the LanguageResource
   * @return the LanguageResource that was requested
   */
  public final LanguageResource getLanguageResource(String name) {
    return getLanguageResource(name, null, null, null);
  }
}
