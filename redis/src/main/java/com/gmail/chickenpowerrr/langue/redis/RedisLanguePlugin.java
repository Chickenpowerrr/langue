package com.gmail.chickenpowerrr.langue.redis;

import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdater;

import java.util.Collection;
import java.util.HashSet;

public class RedisLanguePlugin extends LanguePlugin {

  @Override
  public Collection<String> getAvailableLanguageResourceUpdaters() {
    return new HashSet<String>() {{
      add("Redis");
    }};
  }

  @Override
  public LanguageResourceUpdater getLanguageResourceUpdater(String name,
      LanguageResourceCredentials credentials,
      String... channels) {
    if (name.equals("Redis")) {
      return new RedisLanguageResourceUpdater(credentials, channels);
    }
    return null;
  }
}
