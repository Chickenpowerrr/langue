package com.gmail.chickenpowerrr.langue.redis;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdater;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

/**
 * This class can be used to update resources through the channels
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class RedisLanguageResourceUpdater extends LanguageResourceUpdater {

  private final Collection<Closeable> closeables = new HashSet<>();

  /**
   * Connects to the Redis database and adds the listener
   *
   * @param credentials the credentials to connect to the Redis DB
   * @param channels the channels that should get notified when an update comes in
   */
  public RedisLanguageResourceUpdater(LanguageResourceCredentials credentials, String... channels) {
    super(channels);
    RedisDatabase redisDatabase = new RedisDatabase(credentials);
    this.closeables.add(redisDatabase);
    CompletableFuture.runAsync(() ->
        this.closeables.add(new RedisListener(redisDatabase.getJedis(), this, channels)));
  }

  @Override
  public void close() {
    this.closeables.forEach(closeable -> {
      try {
        closeable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
