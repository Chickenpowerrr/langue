package com.gmail.chickenpowerrr.langue.json;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

/**
 * This class allows the Json extension to get loaded
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class JsonLanguePlugin extends LanguePlugin {

  @Override
  public Collection<String> getAvailableLanguageResources() {
    return new HashSet<String>() {{
      add("JSON");
    }};
  }

  @Override
  public LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
      Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    if (name.equals("JSON")) {
      return new JsonLanguageResource(placeholderManager, formatter, credentials);
    }
    return null;
  }
}
