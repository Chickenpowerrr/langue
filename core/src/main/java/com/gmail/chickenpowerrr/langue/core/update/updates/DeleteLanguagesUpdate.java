package com.gmail.chickenpowerrr.langue.core.update.updates;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;

/**
 * This class contains all of the information needed to delete languages from a resource
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
@AllArgsConstructor
public class DeleteLanguagesUpdate implements Update {

  private final Collection<String> languages;

  /**
   * Returns a map with the name of the languages and the objects of the languages that should get
   * deleted
   */
  public Collection<String> getLanguages() {
    return Collections.unmodifiableCollection(this.languages);
  }
}
