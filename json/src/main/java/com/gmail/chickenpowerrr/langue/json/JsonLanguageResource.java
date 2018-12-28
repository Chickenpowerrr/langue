package com.gmail.chickenpowerrr.langue.json;

import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;

import java.util.function.Function;

public class JsonLanguageResource extends LanguageResource {

    private final JsonFile jsonFile;

    public JsonLanguageResource(PlaceholderManager placeholderManager, Function<String, String> formatter,
                                LanguageResourceCredentials credentials) {
        super(placeholderManager, formatter, null);
        this.jsonFile = new JsonFile(credentials);

        super.languages = this.jsonFile.getLanguages();
    }

    @Override
    public void reload() {
        super.languages = this.jsonFile.getLanguages();
    }
}
