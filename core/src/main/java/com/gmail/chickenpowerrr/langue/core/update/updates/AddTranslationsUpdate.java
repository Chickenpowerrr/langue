package com.gmail.chickenpowerrr.langue.core.update.updates;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public class AddTranslationsUpdate implements Update {

  private final Map<String, Map<String, String>> values;

  public Map<String, Map<String, String>> getValues() {
    return Collections.unmodifiableMap(this.values);
  }
}
