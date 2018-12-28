package com.gmail.chickenpowerrr.langue.yaml;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;

import java.util.function.Function;

public class YamlLanguageResource extends LanguageResource {

    private final YamlFile yamlFile;

    public YamlLanguageResource(PlaceholderManager placeholderManager, Function<String, String> formatter,
                                 LanguageResourceCredentials credentials) {
        super(placeholderManager, formatter, null);
        this.yamlFile = new YamlFile(credentials);

        super.languages = this.yamlFile.getLanguages();
    }

    @Override
    public void reload() {
        super.languages = this.yamlFile.getLanguages();
    }
}
