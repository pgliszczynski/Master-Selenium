package org.training.pom.page.component;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.training.pom.page.CartPage;
import org.training.pom.page.base.BasePage;

public class ProductThumbnail extends BasePage {

  private final By viewCartLink = By.cssSelector("a[title='View cart']");

  public ProductThumbnail(WebDriver driver) {
    super(driver);
  }

  @Step("Add {productName} to cart")
  public ProductThumbnail clickAddToCartButton(String productName) {
    By addToCartButton = getAddToCartButtonElement(productName);
    wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    return this;
  }

  private By getAddToCartButtonElement(String productName) {
    return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
  }

  @Step("Click view cart")
  public CartPage clickViewCart() {
    wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
    return new CartPage(driver);
  }
}
