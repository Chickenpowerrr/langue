package com.gmail.chickenpowerrr.langue.core.placeholder;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderManager {

    private static PlaceholderManager instance;

    private final Pattern regexPattern;
    private final PlaceholderPattern placeholderPattern;
    private final Map<String, String> staticPlaceholders;

    public PlaceholderManager(PlaceholderPattern placeholderPattern, Map<String, String> staticPlaceholders) {
        this.placeholderPattern = placeholderPattern == null ? new PlaceholderPattern("%%", "%%") : placeholderPattern;
        this.staticPlaceholders = staticPlaceholders;
        this.regexPattern = this.placeholderPattern.toRegexPattern();
    }

    public PlaceholderManager(PlaceholderPattern placeholderPattern) {
        this(placeholderPattern, new HashMap<>());
    }

    public PlaceholderManager() {
        this(new PlaceholderPattern("%%", "%%"), new HashMap<>());
    }

    public static PlaceholderManager getInstance() {
        return instance == null ? instance = new PlaceholderManager() : instance;
    }

    public String replacePlaceholders(String message, ResourceLanguage language, Map<String, String> messagePlaceholders) {
        Collection<String> knownPlaceholders = getPlaceholders(message);
        for(String placeholder : knownPlaceholders) {
            if(messagePlaceholders != null
                    && messagePlaceholders.containsKey(this.placeholderPattern.getUniquePart(placeholder))) {
                message = message.replace(placeholder,
                        messagePlaceholders.get(this.placeholderPattern.getUniquePart(placeholder)));
            } else if(this.staticPlaceholders.containsKey(this.placeholderPattern.getUniquePart(placeholder))) {
                message = message.replaceFirst(placeholder, this.staticPlaceholders.get(
                        this.placeholderPattern.getUniquePart(placeholder)));
            } else if(language != null) {
                String replacement = language.getMessage(this.placeholderPattern.getUniquePart(placeholder));
                if(replacement != null) {
                    message = message.replaceFirst(placeholder, replacement);
                }
            }
        }
        return message;
    }

    public String replacePlaceholders(String message) {
        return replacePlaceholders(message, null, null);
    }

    private Collection<String> getPlaceholders(String message) {
        Collection<String> placeholders = new HashSet<>();
        Matcher matcher = this.regexPattern.matcher(message);
        while(matcher.find()) {
            placeholders.add(message.substring(matcher.start(0), matcher.end(0)));
        }
        return placeholders;
    }
}
