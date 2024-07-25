package org.training.pom.driver.factory;

import io.qameta.allure.Step;
import org.training.pom.constants.DriverType;

public class DriverManagerFactory {

  private DriverManagerFactory() {
  }

  @Step("Create driver: {driverType}")
  public static DriverManager getManager(DriverType driverType) {
    return switch (driverType) {
      case CHROME -> new ChromeDriverManager();
      case FIREFOX -> new FirefoxDriverManager();
    };
  }
}
