package org.training.pom.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.training.pom.data.BillingAddress;
import org.training.pom.page.base.BasePage;

public class CheckoutPage extends BasePage {

  private final By billingFirstName = By.id("billing_first_name");
  private final By billingLastName = By.id("billing_last_name");
  private final By billingAddress1 = By.id("billing_address_1");
  private final By countryDropDown = By.id("billing_country");
  private final By billingCity = By.id("billing_city");
  private final By billingPostcode = By.id("billing_postcode");
  private final By stateDropDown = By.id("billing_state");
  private final By billingEmail = By.id("billing_email");

  private final By showLoginButton = By.className("showlogin");
  private final By usernameField = By.id("username");
  private final By passwordField = By.id("password");
  private final By loginButton = By.name("login");

  private final By overlay = By.className("blockOverlay");
  private final By placeOrderButton = By.id("place_order");

  private final By directBankTransferRadioButton = By.id("payment_method_bacs");
  private final By cashOnDeliveryRadioButton = By.id("payment_method_cod");

  private final By productName = By.cssSelector("td[class='product-name']");

  private final By errorNotice = By.cssSelector(".woocommerce-error li");

  public CheckoutPage(WebDriver driver) {
    super(driver);
  }

  @Step("Load Checkout page")
  public CheckoutPage load() {
    load("/checkout/");
    return this;
  }

  @Step("Enter first name: {text}")
  public CheckoutPage enterFirstName(String text) {
    sendKeys(billingFirstName, text);
    return this;
  }

  @Step("Enter last name: {text}")
  public CheckoutPage enterLastName(String text) {
    sendKeys(billingLastName, text);
    return this;
  }

  @Step("Enter first line of address: {text}")
  public CheckoutPage enterAddress1(String text) {
    sendKeys(billingAddress1, text);
    return this;
  }

  @Step("Enter city: {text}")
  public CheckoutPage enterCity(String text) {
    sendKeys(billingCity, text);
    return this;
  }

  @Step("Enter postcode: {text}")
  public CheckoutPage enterPostcode(String text) {
    sendKeys(billingPostcode, text);
    return this;
  }

  @Step("Enter email: {text}")
  public CheckoutPage enterEmail(String text) {
    sendKeys(billingEmail, text);
    return this;
  }

  @Step("Enter username: {username}")
  public CheckoutPage enterUsername(String username) {
    sendKeys(usernameField, username);
    return this;
  }

  @Step("Enter password: {password}")
  public CheckoutPage enterPassword(String password) {
    sendKeys(passwordField, password);
    return this;
  }

  private void sendKeys(By by, String text) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(by)).clear();
    wait.until(ExpectedConditions.visibilityOfElementLocated(by)).sendKeys(text);
  }

  @Step("Select country from drop down, country: {country}")
  public CheckoutPage selectCountry(String country) {
/*    Select select = new Select(
        wait.until(ExpectedConditions.visibilityOfElementLocated(countryDropDown))
    );
    select.selectByVisibleText(country);*/

    By alternateCountryDropDown = By.id("select2-billing_country-container");
    wait.until(ExpectedConditions.elementToBeClickable(alternateCountryDropDown)).click();
    WebElement e = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='" + country + "']")));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
    e.click();
    return this;
  }

  @Step("Select state from drop down, state: {state}")
  public CheckoutPage selectState(String state) {
/*    Select select = new Select(driver.findElement(stateDropDown));
    select.selectByVisibleText(state);*/
    By alternateCountryDropDown = By.id("select2-billing_state-container");
    wait.until(ExpectedConditions.elementToBeClickable(alternateCountryDropDown)).click();
    WebElement e = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='" + state + "']")));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
    e.click();
    return this;
  }

  @Step("Enter state: {state}")
  public CheckoutPage enterState(String state) {
    By stateInput = By.id("billing_state");
    wait.until(ExpectedConditions.visibilityOfElementLocated(stateInput)).sendKeys(state);
    return this;
  }

  @Step("Click on show login form button")
  public CheckoutPage showLoginForm() {
    wait.until(ExpectedConditions.elementToBeClickable(showLoginButton)).click();
    return this;
  }

  @Step("Click login button")
  public CheckoutPage login() {
    wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    return this;
  }

  @Step("Click place order button")
  public OrderPage placeOrder() {
    waitForOverlaysToDisappear(overlay);
    wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
    return new OrderPage(driver);
  }

  @Step("Select Direct Bank Transfer option")
  public CheckoutPage selectDirectBankTransfer() {
    waitForOverlaysToDisappear(overlay);
    WebElement e = wait.until(
        ExpectedConditions.elementToBeClickable(directBankTransferRadioButton));
    if (!e.isSelected()) {
      e.click();
    }
    return this;
  }

  @Step("Select Cash on Delivery option")
  public CheckoutPage selectCashOnDelivery() {
    waitForOverlaysToDisappear(overlay);
    WebElement e = wait.until(
        ExpectedConditions.elementToBeClickable(cashOnDeliveryRadioButton));
    if (!e.isSelected()) {
      e.click();
    }
    return this;
  }

  public String getProductName() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
  }

  public String getErrorMessage() {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(errorNotice)).getText();
  }

  @Step("Enter billing details")
  public CheckoutPage setBillingAddress(BillingAddress billingAddress) {
    enterFirstName(billingAddress.getFirstName())
        .enterLastName(billingAddress.getLastName())
        .enterAddress1(billingAddress.getAddress1())
        .selectCountry(billingAddress.getCountry())
        .enterCity(billingAddress.getCity())
        .enterPostcode(billingAddress.getPostcode())
        .enterEmail(billingAddress.getEmail());

    switch (billingAddress.getCountry()) {
      case "United States (US)",
           "India" -> selectState(billingAddress.getState());
      default -> enterState(billingAddress.getState());
    }
    return this;
  }
}
