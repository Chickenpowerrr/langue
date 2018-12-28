package com.gmail.chickenpowerrr.langue.core.plugin;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdater;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

public abstract class LanguePlugin {

    public LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
                                                Function<String, String> formatter,
                                                LanguageResourceCredentials credentials) {
        return null;
    }

    public final LanguageResource getLanguageResource(String name, Function<String, String> formatter,
                                                      LanguageResourceCredentials credentials) {
        return getLanguageResource(name, null, formatter, credentials);
    }

    public final LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
                                                      LanguageResourceCredentials credentials) {
        return getLanguageResource(name, placeholderManager, null, credentials);
    }

    public final LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
                                                      Function<String, String> formatter) {
        return getLanguageResource(name, placeholderManager, formatter, null);
    }

    public final LanguageResource getLanguageResource(String name, LanguageResourceCredentials credentials) {
        return getLanguageResource(name, null, null, credentials);
    }

    public final LanguageResource getLanguageResource(String name, Function<String, String> formatter) {
        return getLanguageResource(name, null, formatter, null);
    }

    public final LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager) {
        return getLanguageResource(name, placeholderManager, null, null);
    }

    public final LanguageResource getLanguageResource(String name) {
        return getLanguageResource(name, null, null, null);
    }

    public Collection<String> getAvailableLanguageResources() {
        return new HashSet<>();
    }

    public LanguageResourceUpdater getLanguageResourceUpdater(String name, LanguageResourceCredentials credentials,
                                                              String... channels) {
        return null;
    }

    public final LanguageResourceUpdater getLanguageResourceUpdater(String name, String... channels) {
        return getLanguageResourceUpdater(name, null, channels);
    }

    public Collection<String> getAvailableLanguageResourceUpdaters() {
        return new HashSet<>();
    }
}
