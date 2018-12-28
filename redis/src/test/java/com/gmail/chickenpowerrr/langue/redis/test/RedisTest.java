package com.gmail.chickenpowerrr.langue.redis.test;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.update.LanguageResourceUpdateManager;
import com.gmail.chickenpowerrr.langue.redis.RedisListener;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class RedisTest {

    private final RedisListener redisListener = new RedisListener(
            new BasicJedis(), new BasicRedisLanguageResourceUpdater("Test"), "Test");

    @Test
    public void addLanguagesDeserializeTest() {
        this.redisListener.onMessage("", "{\"type\":\"add_languages\",\"value\":" +
                "{\"nederlands\":{\"hello_world\":\"Hallo Wereld\",\"bye_world\":\"Doei Wereld\"}," +
                "\"english\":{\"hello_world\":\"Hello World\",\"bye_world\":\"Bye World\"}}}");
    }

    @Test
    public void addTranslationsDeserializeTest() {
        this.redisListener.onMessage("",
                "{\"type\":\"add_translations\",\"value\":" +
                        "{\"nederlands\":{\"hello_world\":\"Hallo Wereld\",\"bye_world\":\"Doei Wereld\"}," +
                        "\"english\":{\"hello_world\":\"Hello World\",\"bye_world\":\"Bye World\"}}}");
    }

    @Test
    public void deleteLanguagesDeserializeTest() {
        this.redisListener.onMessage("", "{\"type\":\"delete_languages\"," +
                "\"value\":[\"english\",\"dutch\"]}");
    }

    @Test
    public void deleteTranslationsDeserializeTest() {
        this.redisListener.onMessage("", "{\"type\":\"delete_translations\"," +
                "\"value\":[\"hello_world\",\"bye_world\"]}");
    }

    @Test
    public void addLanguagesUpdateTest() {
        LanguageResource languageResource = new BasicPlugin().getLanguageResource("BasicResource");
        LanguageResourceUpdateManager.getInstance().subscribe("Test", languageResource);
        assertNull(languageResource.getLanguage("deutsch"));
        assertNotNull(languageResource.getLanguage("nederlands"));
        assertNotNull(languageResource.getLanguage("english"));
        this.redisListener.onMessage("", "{\"type\":\"add_languages\",\"value\":" +
                "{\"deutsch\":{\"hello_world\":\"Hallo Welt\",\"bye_world\":\"Tschüss Welt\"}}}");
        assertNotNull(languageResource.getLanguage("deutsch"));
        assertNotNull(languageResource.getLanguage("nederlands"));
        assertNotNull(languageResource.getLanguage("english"));
        assertEquals("Hallo Welt", languageResource.getMessage("deutsch", "hello_world"));
        assertEquals("Tschüss Welt", languageResource.getMessage("deutsch", "bye_world"));
    }

    @Test
    public void addTranslationsUpdateTest() {
        LanguageResource languageResource = new BasicPlugin().getLanguageResource("BasicResource");
        LanguageResourceUpdateManager.getInstance().subscribe("Test", languageResource);
        assertNull(languageResource.getMessage("nederlands", "hello_there"));
        assertNull("Doei Daar", languageResource.getMessage("nederlands", "bye_there"));
        this.redisListener.onMessage("", "{\"type\":\"add_translations\"," +
                "\"value\":{\"nederlands\":{\"hello_there\":\"Hallo Daar\",\"bye_there\":\"Doei Daar\"}}}");
        assertNotNull(languageResource.getLanguage("nederlands"));
        assertNotNull(languageResource.getLanguage("english"));
        assertNull(languageResource.getLanguage("deutsch"));
        assertEquals("Hallo Daar", languageResource.getMessage("nederlands", "hello_there"));
        assertEquals("Doei Daar", languageResource.getMessage("nederlands", "bye_there"));
    }

    @Test
    public void deleteLanguagesUpdateTest() {
        LanguageResource languageResource = new BasicPlugin().getLanguageResource("BasicResource");
        LanguageResourceUpdateManager.getInstance().subscribe("Test", languageResource);
        assertNotNull(languageResource.getLanguage("nederlands"));
        assertNotNull(languageResource.getLanguage("english"));
        assertNull(languageResource.getLanguage("deutsch"));
        this.redisListener.onMessage("", "{\"type\":\"delete_languages\",\"value\":[\"nederlands\"]}");
        assertNull(languageResource.getLanguage("nederlands"));
        assertNotNull(languageResource.getLanguage("english"));
        assertNull(languageResource.getLanguage("deutsch"));
        assertEquals("Hello World", languageResource.getMessage("english", "hello_world"));
    }

    @Test
    public void deleteTranslationsUpdateTest() {
        LanguageResource languageResource = new BasicPlugin().getLanguageResource("BasicResource");
        LanguageResourceUpdateManager.getInstance().subscribe("Test", languageResource);
        assertNotNull(languageResource.getMessage("english", "hello_world"));
        assertNotNull(languageResource.getMessage("nederlands", "hello_world"));
        this.redisListener.onMessage("", "{\"type\":\"delete_translations\"," +
                "\"value\":[\"hello_world\"]}");
        assertNotNull(languageResource.getLanguage("nederlands"));
        assertNotNull(languageResource.getLanguage("english"));
        assertNull(languageResource.getLanguage("deutsch"));
        assertNull(languageResource.getMessage("english", "hello_world"));
        assertNull(languageResource.getMessage("nederlands", "hello_world"));
    }
}
