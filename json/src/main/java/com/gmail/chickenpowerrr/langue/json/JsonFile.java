package com.gmail.chickenpowerrr.langue.json;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonFile {

    private final File file;
    private final Gson gson = new Gson();

    public JsonFile(LanguageResourceCredentials credentials) {
        this.file = new File(credentials.getString("fileLocation"));
    }

    public Map<String, ResourceLanguage> getLanguages() {
        try(FileReader fileReader = new FileReader(this.file)) {
            Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
            Map<String, Map<String, String>> resourceLanguages = this.gson.fromJson(fileReader, type);

            return resourceLanguages.entrySet().stream()
                    .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(),
                            new ResourceLanguage(entry.getKey(), resourceLanguages.get(entry.getKey()))))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
