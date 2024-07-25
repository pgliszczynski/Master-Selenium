package org.training.pom.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {

  private JsonUtils() {
  }

  public static <T> T deserializeJson(String filePath, Class<T> T)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new ParameterNamesModule());

    try (InputStream is = JsonUtils.class.getClassLoader()
        .getResourceAsStream(filePath)) {
      return objectMapper.readValue(is, T);
    }
  }
}
