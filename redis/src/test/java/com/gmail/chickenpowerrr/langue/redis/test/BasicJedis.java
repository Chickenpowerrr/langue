package com.gmail.chickenpowerrr.langue.redis.test;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class BasicJedis extends Jedis {

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {

    }

    @Override
    public void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels) {

    }
}
