package org.training.pom.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.training.pom.page.base.BasePage;

public class ProductPage extends BasePage {

  private final By productTitle = By.className("product_title");
  private final By addToCartButton = By.name("add-to-cart");
  private final By viewCartLink = By.cssSelector(".woocommerce-message a");

  private String productName;

  public ProductPage(WebDriver driver) {
    super(driver);
  }

  public ProductPage(WebDriver driver, String productName) {
    super(driver);
    this.productName = productName;
  }

  public Boolean isLoaded() {
    return wait.until(ExpectedConditions.textToBe(productTitle, productName));
  }

  @Step("Click add to cart")
  public ProductPage clickAddToCart() {
    wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton)).click();
    return this;
  }

  @Step("Click view cart")
  public CartPage clickViewCart() {
    wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
    return new CartPage(driver);
  }
}
