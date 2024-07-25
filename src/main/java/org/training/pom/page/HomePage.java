package org.training.pom.page;

import io.qameta.allure.Step;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.training.pom.page.base.BasePage;
import org.training.pom.page.component.ProductThumbnail;
import org.training.pom.page.component.StoreHeader;

public class HomePage extends BasePage {

  private final By featureProductList = By.className("ast-loop-product__link");
  private final By featureProductImageList = By.className("woocommerce-loop-product__title");

  private final StoreHeader storeHeader;
  private final ProductThumbnail productThumbnail;

  public HomePage(WebDriver driver) {
    super(driver);
    storeHeader = new StoreHeader(driver);
    productThumbnail = new ProductThumbnail(driver);
  }

  @Step("Load home page")
  public HomePage load() {
    load("/");
    wait.until(ExpectedConditions.titleContains("AskOmDch"));
    return this;
  }

  @Step("Click random Featured Product")
  public ProductPage clickRandomFeaturedProduct() {
    List<WebElement> products = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(featureProductList));
    List<WebElement> productIamgeList = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(featureProductImageList));

    int elementIndex = new Random().nextInt(products.size());
    WebElement product = products.get(elementIndex);
    WebElement productImage = productIamgeList.get(elementIndex);

    String productName
        = product.findElement(By.className("woocommerce-loop-product__title")).getText();

    wait.until(ExpectedConditions.elementToBeClickable(productImage));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productImage);
    productImage.click();
    return new ProductPage(driver, productName);
  }

  public StoreHeader getStoreHeader() {
    return storeHeader;
  }

  public ProductThumbnail getProductThumbnail() {
    return productThumbnail;
  }
}
