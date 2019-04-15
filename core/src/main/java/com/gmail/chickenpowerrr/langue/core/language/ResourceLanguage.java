package com.gmail.chickenpowerrr.langue.core.language;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class ResourceLanguage {

  @Getter private final String name;
  private final Map<String, String> translations;

  public String getMessage(String key) {
    return this.translations.get(key);
  }

  public void addTranslations(Map<String, String> translations) {
    this.translations.putAll(translations);
  }

  public void removeTranslations(Collection<String> translations) {
    translations.forEach(this.translations::remove);
  }

  @Override
  public String toString() {
    return "{name=\"" + this.name + "\", translations=" + this.translations + "}";
  }
}
