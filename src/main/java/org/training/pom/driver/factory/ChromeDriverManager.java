package org.training.pom.driver.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager implements DriverManager {

  @Override
  @Step("Create ChromeDriver instance")
  public WebDriver createDriver() {
    WebDriverManager.chromedriver().cachePath("drivers").setup();
    WebDriver driver = new ChromeDriver();
    driver.manage().window().maximize();
    return driver;
  }
}
