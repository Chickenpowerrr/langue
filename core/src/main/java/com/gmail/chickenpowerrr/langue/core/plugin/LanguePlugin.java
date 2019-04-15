package com.gmail.chickenpowerrr.langue.core.plugin;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdater;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

/**
 * This is the class every implementation should extend. It handles the basic logic an extension
 * should have
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public abstract class LanguePlugin {

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
    return null;
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

  /**
   * Returns the names of all available language resources
   */
  public Collection<String> getAvailableLanguageResources() {
    return new HashSet<>();
  }

  /**
   * Returns a LanguageResourceUpdater based on the name, the credentials for the updater and the
   * channels it should use
   *
   * @param name the name of the LanguageResourceUpdater
   * @param credentials the credentials needed to login to the resource
   * @param channels the channels the updater should subscribe to
   * @return a LanguageResourceUpdater based on the name, credentials for the updater and the
   * channels it should use
   */
  public LanguageResourceUpdater getLanguageResourceUpdater(String name,
      LanguageResourceCredentials credentials,
      String... channels) {
    return null;
  }

  /**
   * Returns a LanguageResourceUpdater based on the name and the credentials for the updater
   *
   * @param name the name of the LanguageResourceUpdater
   * @param channels the channels the updater should subscribe to
   * @return a LanguageResourceUpdater based on the name, credentials for the updater and the
   * channels it should use
   */
  public final LanguageResourceUpdater getLanguageResourceUpdater(String name, String... channels) {
    return getLanguageResourceUpdater(name, null, channels);
  }

  /**
   * Returns the names of all available language updaters
   */
  public Collection<String> getAvailableLanguageResourceUpdaters() {
    return new HashSet<>();
  }
}
