package com.gmail.chickenpowerrr.langue.redis;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdater;

import java.util.concurrent.CompletableFuture;

public class RedisLanguageResourceUpdater extends LanguageResourceUpdater {

    public RedisLanguageResourceUpdater(LanguageResourceCredentials credentials, String... channels) {
        super(channels);
        RedisDatabase redisDatabase = new RedisDatabase(credentials);
        CompletableFuture.runAsync(() -> new RedisListener(redisDatabase.getJedis(), this, channels));
    }
}
