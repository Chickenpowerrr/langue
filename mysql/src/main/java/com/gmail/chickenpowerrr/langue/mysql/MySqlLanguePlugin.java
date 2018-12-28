package com.gmail.chickenpowerrr.langue.mysql;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

public class MySqlLanguePlugin extends LanguePlugin {

    @Override
    public Collection<String> getAvailableLanguageResources() {
        return new HashSet<String>() {{
            add("MySQL");
        }};
    }

    @Override
    public LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
                                                Function<String, String> formatter,
                                                LanguageResourceCredentials credentials) {
        if(name.equals("MySQL")) {
            return new MySqlLanguageResource(placeholderManager, formatter, credentials);
        }
        return null;
    }
}
