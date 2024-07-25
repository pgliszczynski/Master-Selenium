package org.training.pom.page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.training.pom.page.base.BasePage;

public class CartPage extends BasePage {

/*  private final By productName = By.cssSelector("td[class='product-name'] a");
  private final By checkoutButton = By.cssSelector(".checkout-button");
  private final By cartHeading = By.cssSelector(".has-text-align-center");*/

  @FindBy(css = "td[class='product-name'] a")
  private WebElement productName;

  @FindBy(css = ".checkout-button")
  @CacheLookup
  private WebElement checkoutButton;

  @FindBy(css = ".has-text-align-center")
  private WebElement cartHeading;

  public CartPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
  }

  @Step("Go to checkout")
  public CheckoutPage checkout() {
    wait.until(
        ExpectedConditions.elementToBeClickable(checkoutButton)
    ).click();
    return new CheckoutPage(driver);
  }

  public boolean isLoaded() {
    return wait.until(ExpectedConditions.textToBePresentInElement(cartHeading, "Cart"));
  }

  public String getProductName() {
    return wait.until(
        ExpectedConditions.visibilityOf(productName)
    ).getText();
  }
}
