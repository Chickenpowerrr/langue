package com.gmail.chickenpowerrr.langue.redis;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Closeable;

/**
 * This class contains all of the information needed to connect to a RedisDatabase
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class RedisDatabase implements Closeable {

  private final JedisPool jedisPool;

  /**
   * Creates a connection with the RedisDatabase
   *
   * @param credentials the credentials needed to connect to the database
   */
  public RedisDatabase(LanguageResourceCredentials credentials) {
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setMaxTotal(credentials.getInt("maxPoolSize"));

    this.jedisPool = new JedisPool(jedisPoolConfig, credentials.getString("host"),
        credentials.getInt("port"));
  }

  /**
   * Returns the Jedis instance used to communicate through Redis Pub/Sub
   */
  public Jedis getJedis() {
    return this.jedisPool.getResource();
  }

  @Override
  public void close() {
    if (!this.jedisPool.isClosed()) {
      this.jedisPool.close();
    }
  }
}
