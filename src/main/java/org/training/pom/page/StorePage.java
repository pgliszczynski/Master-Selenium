package org.training.pom.page;

import io.qameta.allure.Step;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.training.pom.data.Product;
import org.training.pom.page.base.BasePage;
import org.training.pom.page.component.ProductThumbnail;

public class StorePage extends BasePage {

  private final By searchBar = By.id("woocommerce-product-search-field-0");
  private final By searchButton = By.cssSelector("button[value='Search']");
  private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");

  private final By productImages = By.className("woocommerce-loop-product__title");
  private final By productList = By.className("ast-loop-product__link");

  private final By noProductFoundMessage = By.className("woocommerce-no-products-found");
  private final ProductThumbnail productThumbnail;

  public StorePage(WebDriver driver) {
    super(driver);
    productThumbnail = new ProductThumbnail(driver);
  }

  @Step("Load Store Page")
  public StorePage load() {
    load("/store");
    return this;
  }

  @Step("Search for text: {text}")
  public StorePage search(String text) {
    return enterTextInSearchBar(text)
        .clickSearchButton();
  }

  @Step("Search for product: {product.name}")
  public ProductPage search(Product product) {
    enterTextInSearchBar(product.getName())
        .clickSearchButton();
    return new ProductPage(driver, product.getName());
  }

  @Step("Enter \"{text}\" text in search bar")
  private StorePage enterTextInSearchBar(String text) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar)).sendKeys(text);
    return this;
  }

  @Step("Click search button")
  private StorePage clickSearchButton() {
    wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    return this;
  }

  @Step("Click product thumbnail, product: {productName}")
  public ProductPage clickProductLink(String productName) {
    By productLink = getProductLink(productName);
    WebElement e = wait.until(ExpectedConditions.elementToBeClickable(productLink));

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
    e.click();
    return new ProductPage(driver, productName);
  }

  private By getProductLink(String productName) {
    return By.xpath(
        "//h2[text()=\"" + productName
            + "\"]/parent::a[@class=\"ast-loop-product__link\"]/parent::div/preceding-sibling::div/a");
  }

  public boolean isSearchLoaded(String searchTerm) {
    return wait.until(ExpectedConditions.urlContains(searchTerm));
  }

  @Step("Click random product on Store Page")
  public ProductPage clickRandomProduct() {
    List<WebElement> products = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(productList));
    List<WebElement> productIamgeList = wait.until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy(productImages));

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

  public ProductThumbnail getProductThumbnail() {
    return productThumbnail;
  }

  public boolean isLoaded() {
    return wait.until(ExpectedConditions.urlContains("/store"));
  }

  public String getTitle() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
  }

  public boolean isNoProductMessage() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(noProductFoundMessage))
               .isDisplayed();
  }
}
