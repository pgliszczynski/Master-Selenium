package org.training.pom.driver.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverManager implements DriverManager {

  @Override
  @Step("Create FirefoxDriver instance")
  public WebDriver createDriver() {
    WebDriverManager.firefoxdriver().cachePath("drivers").setup();
    WebDriver driver = new FirefoxDriver();
    driver.manage().window().maximize();
    return driver;
  }
}
