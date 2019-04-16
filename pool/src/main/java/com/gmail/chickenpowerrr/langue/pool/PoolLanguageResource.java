package com.gmail.chickenpowerrr.langue.pool;

import com.gmail.chickenpowerrr.langue.core.resource.LanguageResource;
import com.gmail.chickenpowerrr.langue.core.resource.LanguageResourceCredentials;
import java.util.Map;

/**
 * This class combines multiple resources to one central resource
 *
 * @author Chickenpowerrr
 * @since 1.0.0
 */
public class PoolLanguageResource extends LanguageResource {

  private final Map<String, LanguageResource> languagesResources;

  /**
   * Combines all of the resources based on the resources given in the credentials
   *
   * @param credentials contains all of the available LanguageResource
   */
  @SuppressWarnings("unchecked")
  public PoolLanguageResource(LanguageResourceCredentials credentials) {
    super(null, null, null);

    this.languagesResources = (Map<String, LanguageResource>) credentials.getObject("parts");
  }

  @Override
  public String getMessage(String language, String key) {
    return getMessage(language, key, null);
  }

  @Override
  public String getMessage(String language, String key, Map<String, String> dynamicPlaceholders) {
    if (key.contains(".")) {
      for (String resource : this.languagesResources.keySet()) {
        if (key.startsWith(resource + ".")) {
          String translation = this.languagesResources.get(resource).getMessage(
              language, key.replaceFirst(resource + "\\.", ""), false);
          if (translation != null) {
            if (dynamicPlaceholders != null) {
              return this.placeholderManager.replacePlaceholders(translation,
                  this.languagesResources.get(resource).getLanguage(language), dynamicPlaceholders);
            } else {
              return translation;
            }
          }
        }
      }
    }

    for (LanguageResource resource : this.languagesResources.values()) {
      String translation = resource.getMessage(language, key, false);
      if (translation != null) {
        if (dynamicPlaceholders != null) {
          return this.placeholderManager
              .replacePlaceholders(translation, resource.getLanguage(language),
                  dynamicPlaceholders);
        } else {
          return translation;
        }
      }
    }
    return null;
  }

  @Override
  public void reload() {
    this.languagesResources.values().forEach(LanguageResource::reload);
  }
}
