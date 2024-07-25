package org.training.pom.utils.faker;

import net.datafaker.Faker;

public class FakerUtils {

  public static final Faker FAKER = new Faker();
  public static final String EASY_PASSWORD = "12345";

  private FakerUtils() {
  }

  public static String generateRandomUsername() {
    return FAKER.internet().username();
  }

  public static String generateRandomEmail() {
    return generateRandomUsername() + "@test.com";
  }
}
