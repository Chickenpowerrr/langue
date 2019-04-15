package com.gmail.chickenpowerrr.langue.core.placeholder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * This class represents a placeholder inside of a message
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class PlaceholderPattern {

  private final String begin;
  private final String end;

  /**
   * Turns the placeholder into a regex pattern
   *
   * @return the regex pattern that represents the current placeholder
   */
  public Pattern toRegexPattern() {
    return Pattern.compile(this.begin + ".+?" + this.end);
  }

  /**
   * Returns the unique part of the placeholder (e.g. 'name' in '%%name%%')
   *
   * @param completePlaceholder the complete placeholder, as it's found in a message
   * @return the unique part of the placeholder (e.g. 'name' in '%%name%%')
   */
  public String getUniquePart(String completePlaceholder) {
    return completePlaceholder
        .substring(this.begin.length(), completePlaceholder.length() - this.end.length());
  }

  /**
   * Turns an unique part into a placeholder
   *
   * @param uniquePart the unique part of the placeholder (e.g. 'name' in '%%name%%')
   * @return the placeholder with it's prefix and suffix
   */
  public String toPlaceholder(String uniquePart) {
    return this.begin + uniquePart + this.end;
  }
}
