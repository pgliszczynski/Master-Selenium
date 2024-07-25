package org.training.pom.page.component;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.training.pom.page.StorePage;
import org.training.pom.page.base.BasePage;

public class StoreHeader extends BasePage {

  private final By storeMenuLink = By.cssSelector("#menu-item-1227 > a");

  public StoreHeader(WebDriver driver) {
    super(driver);
  }

  @Step("Navigate to store using Main Menu")
  public StorePage navigateToStoreUsingMenu() {
    wait.until(ExpectedConditions.elementToBeClickable(storeMenuLink)).click();
    return new StorePage(driver);
  }
}
