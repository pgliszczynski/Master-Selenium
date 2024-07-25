package org.training.pom.utils.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

  private PropertyUtils() {
  }

  public static Properties propertyLoader(String filePath) {
    Properties properties = new Properties();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      properties.load(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }
}
