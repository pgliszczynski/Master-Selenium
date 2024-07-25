package org.training.pom.junit.test.base;

import org.openqa.selenium.WebDriver;
import org.training.pom.driver.DriverManagerOriginal;

public abstract class BaseTest {

  protected WebDriver driver;

  //@BeforeEach
  public void startDriver() {
    driver = new DriverManagerOriginal().initializeDriver(null);
    System.out.println("CURRENT THREAD: " + Thread.currentThread().getName() + ", "
        + "DRIVER = " + driver);
    //driver = new DriverManager().initializeDriver(browser);
  }

  //@AfterEach
  public void quitDriver() {
    System.out.println("CURRENT THREAD: " + Thread.currentThread().getName() + ", "
        + "DRIVER = " + driver);
    driver.quit();
  }
}
