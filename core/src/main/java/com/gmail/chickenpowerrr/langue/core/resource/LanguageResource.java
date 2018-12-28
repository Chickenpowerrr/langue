package com.gmail.chickenpowerrr.langue.core.resource;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.update.updates.*;

import java.util.Map;
import java.util.function.Function;

public abstract class LanguageResource {

    private final Function<String, String> formatter;
    protected final PlaceholderManager placeholderManager;

    protected Map<String, ResourceLanguage> languages;

    public LanguageResource(PlaceholderManager placeholderManager, Function<String, String> formatter,
                            Map<String, ResourceLanguage> languages) {
        this.placeholderManager = placeholderManager == null ? new PlaceholderManager() : placeholderManager;
        this.formatter = formatter;
        this.languages = languages;
    }

    public ResourceLanguage getLanguage(String name) {
        return this.languages.get(name);
    }

    public String getMessage(String language, String key, boolean replacePlaceholders) {
        if(this.languages.containsKey(language)) {
            String message = this.languages.get(language).getMessage(key);
            if(message != null) {
                if(replacePlaceholders) {
                    message = this.placeholderManager.replacePlaceholders(
                            message, this.languages.get(language), null);
                }
                if(this.formatter != null) {
                    return this.formatter.apply(message);
                } else {
                    return message;
                }
            }
        }
        return null;
    }

    public String getMessage(String language, String key) {
        return getMessage(language, key, true);
    }

    public String getMessage(String language, String key, Map<String, String> dynamicPlaceholders) {
        return this.placeholderManager.replacePlaceholders(
                getMessage(language, key, false), this.languages.get(language), dynamicPlaceholders);
    }

    public abstract void reload();

    public boolean acceptsUpdate() {
        return true;
    }

    public void update(Update update) {
        if(update instanceof AddLanguagesUpdate) {
            update((AddLanguagesUpdate) update);
        } else if(update instanceof AddTranslationsUpdate) {
            update((AddTranslationsUpdate) update);
        } else if(update instanceof DeleteLanguagesUpdate) {
            update((DeleteLanguagesUpdate) update);
        } else if(update instanceof DeleteTranslationsUpdate) {
            update((DeleteTranslationsUpdate) update);
        } else {
            throw new IllegalArgumentException(update.getClass() + " is not a valid update");
        }
    }

    private void update(AddLanguagesUpdate addLanguagesUpdate) {
        if(acceptsUpdate()) {
            this.languages.putAll(addLanguagesUpdate.getLanguages());
        }
    }

    private void update(AddTranslationsUpdate addTranslationsUpdate) {
        if(acceptsUpdate()) {
            addTranslationsUpdate.getValues().forEach((language, translations) -> {
                if(this.languages.containsKey(language)) {
                    this.languages.get(language).addTranslations(translations);
                }
            });
        }
    }

    private void update(DeleteLanguagesUpdate deleteLanguagesUpdate) {
        if(acceptsUpdate()) {
            deleteLanguagesUpdate.getLanguages().forEach(this.languages::remove);
        }
    }

    private void update(DeleteTranslationsUpdate deleteTranslationsUpdate) {
        if(acceptsUpdate()) {
            this.languages.values().forEach(language -> language.removeTranslations(
                    deleteTranslationsUpdate.getMessageKeys()));
        }
    }
}
