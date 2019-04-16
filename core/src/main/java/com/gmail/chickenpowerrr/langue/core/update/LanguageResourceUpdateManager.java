package com.gmail.chickenpowerrr.langue.core.update;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.update.updates.Update;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This class contains all of the of the information needed to link an updater to the resource
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class LanguageResourceUpdateManager {

  private static LanguageResourceUpdateManager instance;

  private final Map<String, Collection<LanguageResource>> updateChannels = new HashMap<>();

  private LanguageResourceUpdateManager() {
  }

  /**
   * Returns a lazy instance of this class
   */
  public static LanguageResourceUpdateManager getInstance() {
    return instance == null ? instance = new LanguageResourceUpdateManager() : instance;
  }

  /**
   * Subscribes a resource to an updating channel
   *
   * @param channel the channel the resource should subscribe to in order to get updated
   * @param languageResource the resource that should get updated
   */
  public void subscribe(String channel, LanguageResource languageResource) {
    if (languageResource.acceptsUpdates()) {
      if (!this.updateChannels.containsKey(channel)) {
        this.updateChannels.put(channel, new HashSet<>());
      }
      this.updateChannels.get(channel).add(languageResource);
    } else {
      throw new IllegalStateException("This resource doesn't accept updates");
    }
  }

  /**
   * Unsubscribes a resource from an updating channel
   *
   * @param channel the channel the resource was listening to
   * @param languageResource the resource that doesn't want to listen to the channel anymore
   */
  public void unsubscribe(String channel, LanguageResource languageResource) {
    if (this.updateChannels.containsKey(channel)) {
      this.updateChannels.get(channel).remove(languageResource);
    }
  }

  /**
   * Unsubscribes a resource from updates
   *
   * @param languageResource the resource that doesn't want to get updated anymore
   */
  public void unsubscribe(LanguageResource languageResource) {
    this.updateChannels.values().forEach(channel -> channel.remove(languageResource));
  }

  /**
   * Updates all resources on the given channel
   *
   * @param channel the update channel all of the targets are subscribed to
   * @param update the update that should get executed
   */
  public void update(String channel, Update update) {
    if (this.updateChannels.containsKey(channel)) {
      this.updateChannels.get(channel).forEach(languageResource -> languageResource.update(update));
    }
  }
}
