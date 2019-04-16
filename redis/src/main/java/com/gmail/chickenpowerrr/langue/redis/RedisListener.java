package com.gmail.chickenpowerrr.langue.redis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.io.Closeable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * This class is used to handle the updates through the Redis Pub/Sub
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class RedisListener extends JedisPubSub implements Closeable {

  private final Jedis jedis;
  private final JsonParser jsonParser = new JsonParser();
  private final Gson gson = new Gson();
  private final Type mapType = new TypeToken<Map<String, Map<String, String>>>() {}.getType();
  private final Type collectionType = new TypeToken<Collection<String>>() {}.getType();
  private final RedisLanguageResourceUpdater updater;

  /**
   * Subscribes to the Pub/Sub
   *
   * @param jedis the Jedis instance with the connection to the Redis DB
   * @param updater the object that passes updates to the resources
   * @param channels the Pub/Sub channels this listener uses
   */
  public RedisListener(Jedis jedis, RedisLanguageResourceUpdater updater, String... channels) {
    this.jedis = jedis;
    this.jedis.subscribe(this, channels);
    this.updater = updater;
  }

  @Override
  public void onMessage(String channel, String message) {
    JsonObject totalObject = this.jsonParser.parse(message).getAsJsonObject();
    switch (totalObject.get("type").getAsString().toLowerCase()) {
      case "add_languages":
        this.updater.addLanguages(this.gson.fromJson(totalObject.get("value"), this.mapType));
        break;
      case "add_translations":
        this.updater.addTranslations(this.gson.fromJson(totalObject.get("value"), this.mapType));
        break;
      case "delete_languages":
        this.updater
            .deleteLanguages(this.gson.fromJson(totalObject.get("value"), this.collectionType));
        break;
      case "delete_translations":
        this.updater
            .deleteTranslations(this.gson.fromJson(totalObject.get("value"), this.collectionType));
        break;
      default:
        break;
    }
  }

  @Override
  public void close() {
    if (this.jedis.isConnected()) {
      this.jedis.close();
    }
  }
}
