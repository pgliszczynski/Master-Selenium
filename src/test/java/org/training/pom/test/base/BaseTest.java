package org.training.pom.test.base;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.restassured.http.Cookies;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.training.pom.constants.DriverType;
import org.training.pom.driver.factory.DriverManagerFactory;
import org.training.pom.utils.CookieUtils;

public abstract class BaseTest {

  private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

  @Parameters("browser")
  @BeforeMethod
  public synchronized void startDriver(@Optional String browser) {
    //driver.set(new DriverManagerOriginal().initializeDriver(browser));
    DriverType localBrowser = DriverType.valueOf(
        System.getProperty("browser", java.util.Optional.ofNullable(browser).orElse("CHROME"))
    );
    driver.set(DriverManagerFactory.getManager(localBrowser).createDriver());
    System.out.println("CURRENT THREAD: " + Thread.currentThread().getName() + ", "
        + "DRIVER = " + getDriver());
    //driver = new DriverManager().initializeDriver(browser);
  }

  @AfterMethod
  public synchronized void quitDriver(ITestResult result) throws IOException, InterruptedException {
    System.out.println("CURRENT THREAD: " + Thread.currentThread().getName() + ", "
        + "DRIVER = " + getDriver());

    if (result.getStatus() == ITestResult.FAILURE) {
      String browserName = ((RemoteWebDriver) driver.get()).getCapabilities().getBrowserName();
      File destFile = new File("scr" + File.separator + browserName + File.separator
          + result.getTestClass().getRealClass().getSimpleName() + "_"
          + result.getMethod().getMethodName());

      takeFullPageScreenshot(destFile);
    }

    driver.get().quit();
  }

  private void takeScreenshot(File destFile) throws IOException {
    File srcFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(srcFile, destFile);
  }

  private void takeFullPageScreenshot(File destFile) throws InterruptedException, IOException {
    Shutterbug.shootPage(driver.get(), Capture.FULL_SCROLL, true)
              .withName(destFile.getName())
              .save(destFile.getParentFile().getAbsolutePath());
  }

  public void injectCookiesToBrowser(Cookies cookies) {
    List<Cookie> seleniumCookies = CookieUtils.mapToSeleniumCookies(cookies);
    for (Cookie cookie : seleniumCookies) {
      driver.get().manage().addCookie(cookie);
    }
  }

  protected WebDriver getDriver() {
    return driver.get();
  }
}
