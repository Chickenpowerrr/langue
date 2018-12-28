package com.gmail.chickenpowerrr.langue.core.resource;

import java.util.HashMap;
import java.util.Map;

public class LanguageResourceCredentials {

    private final Map<String, Object> properties;

    /**
     * Creates a new, empty instance of the Properties
     *
     */
    public LanguageResourceCredentials() {
        this(new HashMap<>());
    }

    private LanguageResourceCredentials(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * Add a property to the cache
     *
     * @param key   the key that is used to save and
     *              request the data
     * @param value the value that should be saved
     * @return the current instance
     */
    public LanguageResourceCredentials addProperty(String key, int value) {
        this.properties.put(key, value);
        return this;
    }

    /**
     * Add a property to the cache
     *
     * @param key   the key that is used to save and
     *              request the data
     * @param value the value that should be saved
     * @return the current instance
     */
    public LanguageResourceCredentials addProperty(String key, double value) {
        this.properties.put(key, value);
        return this;
    }

    /**
     * Add a property to the cache
     *
     * @param key   the key that is used to save and
     *              request the data
     * @param value the value that should be saved
     * @return the current instance
     */
    public LanguageResourceCredentials addProperty(String key, Object value) {
        this.properties.put(key, value);
        return this;
    }

    /**
     * Add a property to the cache
     *
     * @param key   the key that is used to save and
     *              request the data
     * @param value the value that should be saved
     * @return the current instance
     */
    public LanguageResourceCredentials addProperty(String key, String value) {
        this.properties.put(key, value);
        return this;
    }

    /**
     * Add a property to the cache
     *
     * @param key   the key that is used to save and
     *              request the data
     * @param value the value that should be saved
     * @return the current instance
     */
    public LanguageResourceCredentials addProperty(String key, long value) {
        this.properties.put(key, value);
        return this;
    }

    /**
     * Get a property from the cache
     *
     * @param key the key that is used to save and
     *            request the data
     * @return the cached data
     */
    public double getDouble(String key) {
        return (double) this.properties.get(key);
    }

    /**
     * Get a property from the cache
     *
     * @param key the key that is used to save and
     *            request the data
     * @return the cached data
     */
    public int getInt(String key) {
        return (int) this.properties.get(key);
    }

    /**
     * Get a property from the cache
     *
     * @param key the key that is used to save and
     *            request the data
     * @return the cached data
     */
    public Object getObject(String key) {
        return this.properties.get(key);
    }

    /**
     * Get a property from the cache
     *
     * @param key the key that is used to save and
     *            request the data
     * @return the cached data
     */
    public String getString(String key) {
        return (String) this.properties.get(key);
    }

    /**
     * Get a property from the cache
     *
     * @param key the key that is used to save and
     *            request the data
     * @return the cached data
     */
    public long getLong(String key) {
        return (long) this.properties.get(key);
    }

    /**
     * Creates a new instance with the same data
     *
     * @return a new instance with the same data
     */
    @Override
    protected LanguageResourceCredentials clone() {
        return new LanguageResourceCredentials(new HashMap<>(this.properties));
    }

    /**
     * Tells if the cache contains a given key
     *
     * @param key the key that is used to save and
     *            request the data
     * @return true if the cache contains the given key,
     *         false if not
     */
    public boolean has(String key) {
        return this.properties.containsKey(key);
    }

    /**
     * Tells if the cache contains a multiple given keys
     *
     * @param keys the keys that are used to save and
     *            request the data
     * @return true if the cache contains all of the given keys,
     *         false if not
     */
    public boolean has(String... keys) {
        for(String key : keys) {
            if(!has(key)) {
                return false;
            }
        }
        return true;
    }
}
