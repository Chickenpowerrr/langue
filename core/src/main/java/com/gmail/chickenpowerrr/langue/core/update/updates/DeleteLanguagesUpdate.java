package com.gmail.chickenpowerrr.langue.core.update.updates;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class DeleteLanguagesUpdate implements Update {

  private final Collection<String> languages;

  public Collection<String> getLanguages() {
    return Collections.unmodifiableCollection(this.languages);
  }
}
