package com.gmail.chickenpowerrr.langue.core.test;

import com.gmail.chickenpowerrr.langue.core.language.ResourceLanguage;
import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderManager;
import com.gmail.chickenpowerrr.langue.core.placeholder.PlaceholderPattern;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;

import java.util.HashMap;
import java.util.Map;

public class BasicLanguageResource extends LanguageResource {

  public BasicLanguageResource(Map<String, ResourceLanguage> languages) {
    super(
        new PlaceholderManager(new PlaceholderPattern("%%", "%%"), new HashMap<String, String>() {{
          put("ceo", "John");
        }}), null, languages);
  }

  @Override
  public void reload() {

  }
}
