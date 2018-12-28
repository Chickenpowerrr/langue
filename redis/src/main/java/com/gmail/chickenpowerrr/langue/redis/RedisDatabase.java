package com.gmail.chickenpowerrr.langue.redis;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Closeable;

public class RedisDatabase implements Closeable {

    private final JedisPool jedisPool;

    public RedisDatabase(LanguageResourceCredentials credentials) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(credentials.getInt("maxPoolSize"));

        this.jedisPool = new JedisPool(jedisPoolConfig, credentials.getString("host"),
                credentials.getInt("port"));


    }

    public Jedis getJedis() {
        return this.jedisPool.getResource();
    }

    @Override
    public void close() {
        if(!this.jedisPool.isClosed()) {
            this.jedisPool.close();
        }
    }
}
