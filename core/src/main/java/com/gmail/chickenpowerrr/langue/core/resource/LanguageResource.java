package com.gmail.chickenpowerrr.langue.core.resource;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.update.updates.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class represents any resource that is able to give the required information for certain
 * languages
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public abstract class LanguageResource {

  private final Function<String, String> formatter;
  protected final PlaceholderManager placeholderManager;

  protected Map<String, ResourceLanguage> languages;

  /**
   * Creates a LanguageResource based on the way placeholders should get handled, messages formatted
   * and the already existing languages
   *
   * @param placeholderManager the way the placeholders should get handled
   * @param formatter the way messages should get formatted
   * @param languages the already known languages
   */
  public LanguageResource(PlaceholderManager placeholderManager, Function<String, String> formatter,
      Map<String, ResourceLanguage> languages) {
    this.placeholderManager =
        placeholderManager == null ? new PlaceholderManager() : placeholderManager;
    this.formatter = formatter;
    this.languages = languages == null ? new HashMap<>() : languages;
  }

  /**
   * Returns a language if it's name matches the given name
   *
   * @param name the name of the language
   * @return the language that matches the given name
   */
  public ResourceLanguage getLanguage(String name) {
    return this.languages.get(name);
  }

  /**
   * Returns the message which key corresponds with the given key in the requested language
   *
   * @param language the language of the message
   * @param key the requested message
   * @param replacePlaceholders if the placeholders should get replaced
   * @return the requested message in the right language and optionally replaced placeholders
   */
  public String getMessage(String language, String key, boolean replacePlaceholders) {
    if (this.languages.containsKey(language)) {
      String message = this.languages.get(language).getMessage(key);
      if (message != null) {
        if (replacePlaceholders) {
          message = this.placeholderManager.replacePlaceholders(
              message, this.languages.get(language), null);
        }
        if (this.formatter != null) {
          return this.formatter.apply(message);
        } else {
          return message;
        }
      }
    }
    return null;
  }

  /**
   * Returns the message which key corresponds with the given key in the requested language with
   * replaced placeholders
   *
   * @param language the language of the message
   * @param key the requested message
   * @return the requested message in the right language and optionally replaced placeholders with
   * replaced placeholders
   */
  public String getMessage(String language, String key) {
    return getMessage(language, key, true);
  }

  /**
   * Returns the message which key corresponds with the given key in the requested language
   *
   * @param language the language of the message
   * @param key the requested message
   * @return the requested message in the right language and optionally replaced placeholders
   */
  public String getMessage(String language, String key, Map<String, String> dynamicPlaceholders) {
    return this.placeholderManager.replacePlaceholders(
        getMessage(language, key, false), this.languages.get(language), dynamicPlaceholders);
  }

  /**
   * Reloads the language resource
   */
  public abstract void reload();

  /**
   * Returns if this resource can get updated by a LanguageResourceUpdater
   */
  public boolean acceptsUpdates() {
    return true;
  }

  /**
   * Updates the translations based on a given update
   *
   * @param update the update that should be preformed
   */
  public void update(Update update) {
    if (acceptsUpdates()) {
      if (update instanceof AddLanguagesUpdate) {
        update((AddLanguagesUpdate) update);
      } else if (update instanceof AddTranslationsUpdate) {
        update((AddTranslationsUpdate) update);
      } else if (update instanceof DeleteLanguagesUpdate) {
        update((DeleteLanguagesUpdate) update);
      } else if (update instanceof DeleteTranslationsUpdate) {
        update((DeleteTranslationsUpdate) update);
      } else {
        throw new IllegalArgumentException(update.getClass() + " is not a valid update");
      }
    }
  }

  /**
   * Adds languages to the resource
   *
   * @param addLanguagesUpdate which languages should get added
   */
  private void update(AddLanguagesUpdate addLanguagesUpdate) {
    if (acceptsUpdates()) {
      this.languages.putAll(addLanguagesUpdate.getLanguages());
    }
  }

  /**
   * Adds translations to the resource
   *
   * @param addTranslationsUpdate which translations should get added
   */
  private void update(AddTranslationsUpdate addTranslationsUpdate) {
    if (acceptsUpdates()) {
      addTranslationsUpdate.getValues().forEach((language, translations) -> {
        if (this.languages.containsKey(language)) {
          this.languages.get(language).addTranslations(translations);
        }
      });
    }
  }

  /**
   * Removes languages from the resource
   *
   * @param deleteLanguagesUpdate which languages should get deleted
   */
  private void update(DeleteLanguagesUpdate deleteLanguagesUpdate) {
    if (acceptsUpdates()) {
      deleteLanguagesUpdate.getLanguages().forEach(this.languages::remove);
    }
  }

  /**
   * Removes translations from the resource
   *
   * @param deleteTranslationsUpdate which translations should get deleted
   */
  private void update(DeleteTranslationsUpdate deleteTranslationsUpdate) {
    if (acceptsUpdates()) {
      this.languages.values().forEach(language -> language.removeTranslations(
          deleteTranslationsUpdate.getMessageKeys()));
    }
  }
}
