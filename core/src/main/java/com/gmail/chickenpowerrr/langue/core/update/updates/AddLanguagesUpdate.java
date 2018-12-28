package com.gmail.chickenpowerrr.langue.core.update.updates;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public class AddLanguagesUpdate implements Update {

    private final Map<String, ResourceLanguage> languages;

    public Map<String, ResourceLanguage> getLanguages() {
        return Collections.unmodifiableMap(this.languages);
    }
}
