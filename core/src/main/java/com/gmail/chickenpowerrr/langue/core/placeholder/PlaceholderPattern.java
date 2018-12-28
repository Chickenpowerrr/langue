package com.gmail.chickenpowerrr.langue.core.placeholder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public class PlaceholderPattern {

    private final String begin;
    private final String end;

    public Pattern toRegexPattern() {
        return Pattern.compile(this.begin + ".+?" + this.end);
    }

    public String getUniquePart(String completePlaceholder) {
        return completePlaceholder.substring(this.begin.length(), completePlaceholder.length() - this.end.length());
    }

    public String toPlaceholder(String uniquePart) {
        return this.begin + uniquePart + this.end;
    }
}
