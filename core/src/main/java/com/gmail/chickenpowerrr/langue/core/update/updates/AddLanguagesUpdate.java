package com.gmail.chickenpowerrr.langue.core.update.updates;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Map;

/**
 * This class contains all of the information needed to add languages to a resource
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
@AllArgsConstructor
public class AddLanguagesUpdate implements Update {

  private final Map<String, ResourceLanguage> languages;

  /**
   * Returns a map with the name of the languages and the objects of the languages that should get
   * added
   */
  public Map<String, ResourceLanguage> getLanguages() {
    return Collections.unmodifiableMap(this.languages);
  }
}
