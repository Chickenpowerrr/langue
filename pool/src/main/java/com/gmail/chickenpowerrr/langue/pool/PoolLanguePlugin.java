package com.gmail.chickenpowerrr.langue.pool;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

/**
 * This class contains all of the methods needed to use a basic Pool that contains the
 * relevant language information
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class PoolLanguePlugin extends LanguePlugin {

  @Override
  public Collection<String> getAvailableLanguageResources() {
    return new HashSet<String>() {{
      add("Pool");
    }};
  }

  @Override
  public LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
      Function<String, String> formatter,
      LanguageResourceCredentials credentials) {
    if (name.equals("Pool")) {
      return new PoolLanguageResource(credentials);
    }
    return null;
  }
}
