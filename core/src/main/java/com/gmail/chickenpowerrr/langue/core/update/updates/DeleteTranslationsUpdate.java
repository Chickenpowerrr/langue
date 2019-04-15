package com.gmail.chickenpowerrr.langue.core.update.updates;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class DeleteTranslationsUpdate implements Update {

  private final Collection<String> messageKeys;

  public Collection<String> getMessageKeys() {
    return Collections.unmodifiableCollection(this.messageKeys);
  }
}
