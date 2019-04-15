package com.gmail.chickenpowerrr.langue.core.placeholder;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles the placeholders in translatable messages
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class PlaceholderManager {

  private static PlaceholderManager instance;

  private final Pattern regexPattern;
  private final PlaceholderPattern placeholderPattern;
  private final Map<String, String> staticPlaceholders;

  /**
   * Creates an instance based on the format of a placeholder and the default placeholders
   *
   * @param placeholderPattern the format of a placeholder
   * @param staticPlaceholders the default placeholders that should always get handled the same
   */
  public PlaceholderManager(PlaceholderPattern placeholderPattern,
      Map<String, String> staticPlaceholders) {
    this.placeholderPattern =
        placeholderPattern == null ? new PlaceholderPattern("%%", "%%") : placeholderPattern;
    this.staticPlaceholders = staticPlaceholders;
    this.regexPattern = this.placeholderPattern.toRegexPattern();
  }

  /**
   * Creates an instance based on the format of a placeholder
   *
   * @param placeholderPattern the format of a placeholder
   */
  public PlaceholderManager(PlaceholderPattern placeholderPattern) {
    this(placeholderPattern, new HashMap<>());
  }

  /**
   * Creates an instance based on the following placeholder pattern: %%placeholder%%
   */
  public PlaceholderManager() {
    this(new PlaceholderPattern("%%", "%%"), new HashMap<>());
  }

  /**
   * Replaces the placeholders in a given message, if the placeholder is not given in the Map, the
   * next step is to look if the placeholder is translatable
   *
   * @param message the message that could contain placeholders
   * @param language the language the translatable placeholders should get translated to
   * @param messagePlaceholders the placeholders that should get set (e.g. a number or a player
   * name)
   * @return the message with the replaced placeholders
   */
  public String replacePlaceholders(String message, ResourceLanguage language,
      Map<String, String> messagePlaceholders) {
    Collection<String> knownPlaceholders = getPlaceholders(message);
    for (String placeholder : knownPlaceholders) {
      if (messagePlaceholders != null
          && messagePlaceholders.containsKey(this.placeholderPattern.getUniquePart(placeholder))) {
        message = message.replace(placeholder,
            messagePlaceholders.get(this.placeholderPattern.getUniquePart(placeholder)));
      } else if (this.staticPlaceholders
          .containsKey(this.placeholderPattern.getUniquePart(placeholder))) {
        message = message.replaceFirst(placeholder, this.staticPlaceholders.get(
            this.placeholderPattern.getUniquePart(placeholder)));
      } else if (language != null) {
        String replacement = language
            .getMessage(this.placeholderPattern.getUniquePart(placeholder));
        if (replacement != null) {
          message = message.replaceFirst(placeholder, replacement);
        }
      }
    }
    return message;
  }

  /**
   * Replaces the static placeholders in the given message
   *
   * @param message the message that could contain static placeholders
   * @return the message with the replaced placeholders
   */
  public String replacePlaceholders(String message) {
    return replacePlaceholders(message, null, null);
  }

  /**
   * Get all placeholders a given String contains
   *
   * @param message the message which placeholders need to be determined
   * @return all placeholders the given String contains
   */
  private Collection<String> getPlaceholders(String message) {
    Collection<String> placeholders = new HashSet<>();
    Matcher matcher = this.regexPattern.matcher(message);
    while (matcher.find()) {
      placeholders.add(message.substring(matcher.start(0), matcher.end(0)));
    }
    return placeholders;
  }
}
