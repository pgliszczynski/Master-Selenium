package org.training.pom.page.base;

import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.training.pom.utils.config.ConfigLoader;

public abstract class BasePage {

  protected final WebDriver driver;
  protected final WebDriverWait wait;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
  }

  @Step("Load page: {endpoint}")
  public void load(String endpoint) {
    String baseUrl = ConfigLoader.getInstance().getBaseUrl();
    driver.get(baseUrl + endpoint);
  }

  @Step("Wait for overlays to disappear")
  public void waitForOverlaysToDisappear(By overlay) {
    List<WebElement> overlays = driver.findElements(overlay);
    System.out.println("OVERLAY SIZE: " + overlays.size());
    if (!overlays.isEmpty()) {
      wait.until(
          ExpectedConditions.invisibilityOfAllElements(overlays)
      );
      System.out.println("OVERLAYS ARE INVISIBLE: ");
    } else {
      System.out.println("OVERLAY NOT FOUND");
    }
  }
}
