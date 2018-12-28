package com.gmail.chickenpowerrr.langue.core.test;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.plugin.LanguePlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

public class BasicPlugin extends LanguePlugin {

    private LanguageResource languageResource = null;

    @Override
    public LanguageResource getLanguageResource(String name, PlaceholderManager placeholderManager,
                                                Function<String, String> formatter,
                                                LanguageResourceCredentials credentials) {
        if(name.equals("BasicResource")) {
            if(languageResource == null) {
                Map<String, ResourceLanguage> languages = new HashMap<>();
                languages.put("english", new ResourceLanguage("english", new HashMap<String, String>() {{
                    put("hello_world", "Hello World");
                    put("test", "This is a test!");
                    put("bank", "You have $%%amount%% on your account");
                    put("yes", "Yes");
                    put("no", "No");
                    put("answer", "The answer is: %%yes%% with %%seconds%% seconds to go");
                    put("ceo", "%%ceo%% is the CEO");
                }}));

                languages.put("nederlands", new ResourceLanguage("nederlands", new HashMap<String, String>() {{
                    put("hello_world", "Hallo Wereld");
                    put("test", "Dit is een test!");
                    put("bank", "Je hebt €%%amount%% op je account");
                    put("yes", "Ja");
                    put("no", "Nee");
                    put("answer", "Het antwoord is: %%yes%% met nog %%seconds%% seconden te gaan");
                    put("ceo", "%%ceo%% is de CEO");
                }}));

                this.languageResource = new BasicLanguageResource(languages);
            }

            return this.languageResource;
        }
        return null;
    }

    @Override
    public Collection<String> getAvailableLanguageResources() {
        return new HashSet<String>() {{
            add("BasicResource");
        }};
    }
}
