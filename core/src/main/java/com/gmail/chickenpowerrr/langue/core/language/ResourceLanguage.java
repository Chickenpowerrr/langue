package com.gmail.chickenpowerrr.langue.core.language;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;

/**
 * This class represents a language, it contains all the translations needed to be able to send
 * messages to a player messages in its own language
 *
 * @author Chickenpowerrr
 * @version 1.0.0
 */
@AllArgsConstructor
public class ResourceLanguage {

  @Getter private final String name;
  private final Map<String, String> translations;

  /**
   * Returns the message based on the key that represents the requested message
   *
   * @param key the translation key
   * @return the message based on the key that represents the requested message
   */
  public String getMessage(String key) {
    return this.translations.get(key);
  }

  /**
   * Updates/adds translations to the language
   *
   * @param translations the messages that need to get updated/added to the language
   */
  public void addTranslations(Map<String, String> translations) {
    this.translations.putAll(translations);
  }

  /**
   * Removes translations from the language
   *
   * @param translations the translations that should get unregistered
   */
  public void removeTranslations(Collection<String> translations) {
    translations.forEach(this.translations::remove);
  }

  @Override
  public String toString() {
    return "{name=\"" + this.name + "\", translations=" + this.translations + "}";
  }
}
