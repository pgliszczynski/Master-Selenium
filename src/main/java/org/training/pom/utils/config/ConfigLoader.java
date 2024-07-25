package org.training.pom.utils.config;

import java.util.Optional;
import java.util.Properties;
import org.training.pom.constants.EnvType;

public class ConfigLoader {

  private static ConfigLoader configLoader;

  private final Properties properties;

  private ConfigLoader() {
    EnvType env = EnvType.valueOf(System.getProperty("env", "STAGE"));
    properties = switch (env) {
      case STAGE -> PropertyUtils.propertyLoader("src/main/resources/stg_config.properties");
      case PRODUCTION -> PropertyUtils.propertyLoader("src/main/resources/prod_config.properties");
    };
  }

  public static ConfigLoader getInstance() {
    if (configLoader == null) {
      configLoader = new ConfigLoader();
    }
    return configLoader;
  }

  public String getBaseUrl() {
    Optional<String> property = Optional.ofNullable(properties.getProperty("baseUrl"));
    return property.orElseThrow(
        () -> new RuntimeException("Property baseUrl is not specified in the config file"));
  }
}
