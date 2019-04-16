package com.gmail.chickenpowerrr.langue.core.update;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.update.updates.AddLanguagesUpdate;
import com.gmail.chickenpowerrr.langue.core.update.updates.AddTranslationsUpdate;
import com.gmail.chickenpowerrr.langue.core.update.updates.DeleteLanguagesUpdate;
import com.gmail.chickenpowerrr.langue.core.update.updates.DeleteTranslationsUpdate;

import java.io.Closeable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class contains all methods needed to be able to update resources
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public abstract class LanguageResourceUpdater implements Closeable {

  private final String[] channels;

  /**
   * Adds all channels this updater should notify when an updates comes in
   *
   * @param channels all channels this updater should notify when an updates comes in
   */
  public LanguageResourceUpdater(String... channels) {
    this.channels = channels;
  }

  /**
   * Adds given languages to all the subscribed resources
   *
   * @param languageInfo a map with the name of the language as the key and the translations as the
   * value
   */
  public void addLanguages(Map<String, Map<String, String>> languageInfo) {
    Map<String, ResourceLanguage> languages = languageInfo.entrySet().stream()
        .map(entry -> new HashMap.SimpleEntry<>(
            entry.getKey(), new ResourceLanguage(entry.getKey(), entry.getValue())))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    for (String channel : this.channels) {
      LanguageResourceUpdateManager.getInstance()
          .update(channel, new AddLanguagesUpdate(languages));
    }
  }

  /**
   * Adds given translations to all the subscribed resources
   *
   * @param translations a map with the name of the language as the key and the translations as the
   * value
   */
  public void addTranslations(Map<String, Map<String, String>> translations) {
    for (String channel : this.channels) {
      LanguageResourceUpdateManager.getInstance()
          .update(channel, new AddTranslationsUpdate(translations));
    }
  }

  /**
   * Deletes the given languages from all of the subscribed resources
   *
   * @param languages the languages that should get deleted
   */
  public void deleteLanguages(Collection<String> languages) {
    for (String channel : this.channels) {
      LanguageResourceUpdateManager.getInstance()
          .update(channel, new DeleteLanguagesUpdate(languages));
    }
  }

  /**
   * Deletes the given translations from all of the subscribed resources
   *
   * @param messageKeys the translations that should get deleted
   */
  public void deleteTranslations(Collection<String> messageKeys) {
    for (String channel : this.channels) {
      LanguageResourceUpdateManager.getInstance()
          .update(channel, new DeleteTranslationsUpdate(messageKeys));
    }
  }

  @Override
  public void close() {

  }
}
