package com.gmail.chickenpowerrr.langue.json.test;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceFactory;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class JsonTest {

    private final LanguageResourceCredentials credentials = new LanguageResourceCredentials().addProperty(
            "fileLocation", new File("resources").getAbsolutePath()
                    .replace("resources", "src/main/resources/test.json"));

    @Test
    public void loadFileTest() {
        new LanguageResourceFactory().getLanguageResource("JSON", this.credentials);
    }

    @Test
    public void loadMessageTest() {
        assertEquals("Hallo Wereld", new LanguageResourceFactory().getLanguageResource(
                "JSON", this.credentials).getMessage("nederlands", "hello_world"));
        assertEquals("Hello World", new LanguageResourceFactory().getLanguageResource(
                "JSON", this.credentials).getMessage("english", "hello_world"));
    }

    @Test
    public void placeholderTest() {
        assertEquals("Red is a color", new LanguageResourceFactory().getLanguageResource(
                "JSON", this.credentials).getMessage("english", "color",
                new HashMap<String, String>() {{
                    put("color", "Red");
                }}));
        assertEquals("Rood is een kleur", new LanguageResourceFactory().getLanguageResource(
                "JSON", this.credentials).getMessage("nederlands", "color",
                new HashMap<String, String>() {{
                    put("color", "Rood");
                }}));
    }
}
