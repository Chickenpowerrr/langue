package com.gmail.chickenpowerrr.langue.core.update;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.update.updates.Update;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LanguageResourceUpdateManager {

  private static LanguageResourceUpdateManager instance;

  private final Map<String, Collection<LanguageResource>> updateChannels = new HashMap<>();

  private LanguageResourceUpdateManager() {
  }

  public static LanguageResourceUpdateManager getInstance() {
    return instance == null ? instance = new LanguageResourceUpdateManager() : instance;
  }

  public void subscribe(String channel, LanguageResource languageResource) {
    if (!this.updateChannels.containsKey(channel)) {
      this.updateChannels.put(channel, new HashSet<>());
    }
    this.updateChannels.get(channel).add(languageResource);
  }

  public void unsubscribe(String channel, LanguageResource languageResource) {
    if (this.updateChannels.containsKey(channel)) {
      this.updateChannels.get(channel).remove(languageResource);
    }
  }

  public void unsubscribe(LanguageResource languageResource) {
    this.updateChannels.values().forEach(channel -> channel.remove(languageResource));
  }

  public void update(String channel, Update update) {
    if (this.updateChannels.containsKey(channel)) {
      this.updateChannels.get(channel).forEach(languageResource -> languageResource.update(update));
    }
  }
}
