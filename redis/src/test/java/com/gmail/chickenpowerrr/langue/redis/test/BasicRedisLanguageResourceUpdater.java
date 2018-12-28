package com.gmail.chickenpowerrr.langue.redis.test;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.redis.RedisLanguageResourceUpdater;

public class BasicRedisLanguageResourceUpdater extends RedisLanguageResourceUpdater {

    public BasicRedisLanguageResourceUpdater(String... channels) {
        super(new LanguageResourceCredentials().addProperty("host", "127.0.0.1").addProperty("port", 3679)
                .addProperty("maxPoolSize", 10), channels);
    }
}
