package com.gmail.chickenpowerrr.langue.core.update;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.update.updates.AddLanguagesUpdate;
import com.gmail.chickenpowerrr.langue.core.update.updates.AddTranslationsUpdate;
import com.gmail.chickenpowerrr.langue.core.update.updates.DeleteLanguagesUpdate;
import com.gmail.chickenpowerrr.langue.core.update.updates.DeleteTranslationsUpdate;

import java.io.Closeable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LanguageResourceUpdater implements Closeable {

    private final String[] channels;

    public LanguageResourceUpdater(String... channels) {
        this.channels = channels;
    }

    public void addLanguages(Map<String, Map<String, String>> languageInfo) {
        Map<String, ResourceLanguage> languages = languageInfo.entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<>(
                        entry.getKey(), new ResourceLanguage(entry.getKey(), entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for(String channel : this.channels) {
            LanguageResourceUpdateManager.getInstance().update(channel, new AddLanguagesUpdate(languages));
        }
    }

    public void addTranslations(Map<String, Map<String, String>> translations) {
        for(String channel : this.channels) {
            LanguageResourceUpdateManager.getInstance().update(channel, new AddTranslationsUpdate(translations));
        }
    }

    public void deleteLanguages(Collection<String> languages) {
        for(String channel : this.channels) {
            LanguageResourceUpdateManager.getInstance().update(channel, new DeleteLanguagesUpdate(languages));
        }
    }

    public void deleteTranslations(Collection<String> messageKeys) {
        for(String channel : this.channels) {
            LanguageResourceUpdateManager.getInstance().update(channel, new DeleteTranslationsUpdate(messageKeys));
        }
    }

    @Override
    public void close() {

    }
}
