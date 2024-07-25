package org.training.pom.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.training.pom.page.base.BasePage;

public class OrderPage extends BasePage {

  private final By orderNotice = By.cssSelector(".woocommerce-notice");

  public OrderPage(WebDriver driver) {
    super(driver);
  }

  public String getOrderNotice() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(orderNotice)).getText();
  }
}
