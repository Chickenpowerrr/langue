package com.gmail.chickenpowerrr.langue.core.update.updates;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Map;

/**
 * This class contains all of the information needed to add translations to a resource
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
@AllArgsConstructor
public class AddTranslationsUpdate implements Update {

  private final Map<String, Map<String, String>> values;

  /**
   * Returns a map with the name of the translations and the objects of the translations that should
   * get added
   */
  public Map<String, Map<String, String>> getValues() {
    return Collections.unmodifiableMap(this.values);
  }
}
