package com.gmail.chickenpowerrr.langue.yaml;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

public class YamlLanguePlugin extends LanguePlugin {

  @Override
  public Collection<String> getAvailableLanguageResources() {
    return new HashSet<String>() {{
      add("Yaml");
    }};
  }

  @Override
  public LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
      Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    if (name.equals("Yaml")) {
      return new YamlLanguageResource(placeholderManager, formatter, credentials);
    }
    return null;
  }
}
