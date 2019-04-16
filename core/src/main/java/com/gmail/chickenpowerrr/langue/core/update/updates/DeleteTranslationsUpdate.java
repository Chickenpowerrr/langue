package com.gmail.chickenpowerrr.langue.core.update.updates;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;

/**
 * This class contains all of the information needed to delete translations from a resource
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
@AllArgsConstructor
public class DeleteTranslationsUpdate implements Update {

  private final Collection<String> messageKeys;

  /**
   * Returns a map with the name of the translations and the objects of the translations that should
   * get deleted
   */
  public Collection<String> getMessageKeys() {
    return Collections.unmodifiableCollection(this.messageKeys);
  }
}
