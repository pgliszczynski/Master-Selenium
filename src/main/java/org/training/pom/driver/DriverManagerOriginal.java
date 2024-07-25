package org.training.pom.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Optional;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.training.pom.constants.DriverType;

public class DriverManagerOriginal {

  public WebDriver initializeDriver(String browser) {
    DriverType localBrowser = DriverType.valueOf(
        System.getProperty("browser", Optional.ofNullable(browser).orElse("CHROME"))
    );

    WebDriver driver = switch (localBrowser) {
      case DriverType.CHROME -> {
        WebDriverManager.chromedriver().cachePath("drivers").setup();
        yield new ChromeDriver();
      }
      case DriverType.FIREFOX -> {
        WebDriverManager.firefoxdriver().cachePath("drivers").setup();
        yield new FirefoxDriver();
      }
    };
    driver.manage().window().maximize();
    //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    return driver;
  }
}
