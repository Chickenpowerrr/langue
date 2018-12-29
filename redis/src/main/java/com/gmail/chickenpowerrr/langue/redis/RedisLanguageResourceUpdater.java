package com.gmail.chickenpowerrr.langue.redis;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdater;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public class RedisLanguageResourceUpdater extends LanguageResourceUpdater {

    private final Collection<Closeable> closeables = new HashSet<>();

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
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
    }
}
