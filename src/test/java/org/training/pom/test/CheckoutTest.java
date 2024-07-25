package org.training.pom.test;

import static org.testng.Assert.assertEquals;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.io.IOException;
import org.testng.annotations.Test;
import org.training.pom.api.actions.BillingApi;
import org.training.pom.api.actions.CartApi;
import org.training.pom.api.actions.SignupApi;
import org.training.pom.data.BillingAddress;
import org.training.pom.data.Product;
import org.training.pom.data.User;
import org.training.pom.data.providers.MyDataProvider;
import org.training.pom.page.CheckoutPage;
import org.training.pom.page.OrderPage;
import org.training.pom.test.base.BaseTest;
import org.training.pom.utils.faker.FakerUtils;

@Epic("Standalone Tests")
@Feature("Checkout tests")
public class CheckoutTest extends BaseTest {

  @Description("""
      While on Checkout Page with Products added to cart, User enters billing information
      and proceeds to order using Direct Bank Transfer without logging it
      """)
  @Story("Guest Checkout")
  @Test(dataProviderClass = MyDataProvider.class, dataProvider = "getBillingAddresses",
      description = "Should be able to checkout as a guest using Direct Bank Transfer")
  void guestCheckoutUsingDirectBangTransfer(BillingAddress billingAddress) throws IOException {
    // Given
    CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
    CartApi cartApi = new CartApi();
    cartApi.addToCart(new Product(1215), 1);
    injectCookiesToBrowser(cartApi.getCookies());
    checkoutPage.load();

    // When
    checkoutPage
        .setBillingAddress(billingAddress)
        .selectDirectBankTransfer();
    OrderPage orderPage = checkoutPage.placeOrder();

    // Then
    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );
  }

  @Description("""
      While on Checkout Page with Products added to cart, User logs in and then enters billing information
      and proceeds to order using Direct Bank Transfer
      """)
  @Story("Logged in user Checkout")
  @Test(dataProviderClass = MyDataProvider.class, dataProvider = "getBillingAddresses",
      description = "Should be able to checkout as logged in user using Direct Bank Transfer")
  void loginAndCheckoutUsingDirectBankTransfer(BillingAddress billingAddress) throws IOException {
    // Given
    SignupApi signupApi = new SignupApi();
    User user = new User(
        FakerUtils.generateRandomUsername(),
        FakerUtils.EASY_PASSWORD,
        FakerUtils.generateRandomEmail()
    );
    signupApi.register(user);

    Product product = new Product(1215);
    CartApi cartApi = new CartApi(signupApi.getCookies());
    cartApi.addToCart(product, 1);

    CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
    injectCookiesToBrowser(signupApi.getCookies());
    checkoutPage.load();

    // When
    checkoutPage
        .setBillingAddress(billingAddress)
        .selectDirectBankTransfer();
    OrderPage orderPage = checkoutPage.placeOrder();

    // Then
    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );
  }

  @Description("""
      While on Checkout Page with Products added to cart, for user with set Billing Address
      User logs in and then enters Billing Information, then proceeds to order
      """)
  @Story("Logged in user Checkout")
  @Test(dataProviderClass = MyDataProvider.class, dataProvider = "getBillingAddresses",
      description = "Should be able to checkout as logged in user with billing address set")
  void loginAndCheckoutWithBillingAddressSet(BillingAddress billingAddress) throws IOException {
    // Given
    SignupApi signupApi = new SignupApi();
    User user = new User(
        FakerUtils.generateRandomUsername(),
        FakerUtils.EASY_PASSWORD,
        FakerUtils.generateRandomEmail()
    );
    signupApi.register(user);

    BillingApi billingApi = new BillingApi(signupApi.getCookies());
    billingApi.setBillingAddress(billingAddress);

    Product product = new Product(1215);
    CartApi cartApi = new CartApi(signupApi.getCookies());
    cartApi.addToCart(product, 1);

    CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
    injectCookiesToBrowser(signupApi.getCookies());
    checkoutPage.load();

    // When
    checkoutPage
        .selectDirectBankTransfer();
    OrderPage orderPage = checkoutPage.placeOrder();

    // Then
    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );
  }

  @Description("""
      While on Checkout Page with Products added to cart, User enters billing information
      and proceeds to order using Cash on Deliver without logging it
      """)
  @Story("Guest Checkout")
  @Test(dataProviderClass = MyDataProvider.class, dataProvider = "getBillingAddresses",
      description = "Should be able to checkout as guest using Cash on Deliver")
  void guestCheckoutUsingDirectCashOnDelivery(BillingAddress billingAddress)
      throws IOException, InterruptedException {
    // Given
    CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
    CartApi cartApi = new CartApi();
    cartApi.addToCart(new Product(1215), 1);
    injectCookiesToBrowser(cartApi.getCookies());
    checkoutPage.load();

    // When
    checkoutPage
        .setBillingAddress(billingAddress)
        .selectCashOnDelivery();
    OrderPage orderPage = checkoutPage.placeOrder();

    // Then
    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );
  }

  @Description("""
      While on Checkout Page with Products added to cart, User logs in and then enters billing information
      and proceeds to order using Cash on Deliver
      """)
  @Story("Logged in user Checkout")
  @Test(dataProviderClass = MyDataProvider.class, dataProvider = "getBillingAddresses",
      description = "Should be able to checkout as logged in user using Cash on Deliver")
  void loginAndCheckoutUsingCashOnDelivery(BillingAddress billingAddress) throws IOException {
    // Given
    SignupApi signupApi = new SignupApi();
    User user = new User(
        FakerUtils.generateRandomUsername(),
        FakerUtils.EASY_PASSWORD,
        FakerUtils.generateRandomEmail()
    );
    signupApi.register(user);

    Product product = new Product(1215);
    CartApi cartApi = new CartApi(signupApi.getCookies());
    cartApi.addToCart(product, 1);

    CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
    injectCookiesToBrowser(signupApi.getCookies());
    checkoutPage.load();

    // When
    checkoutPage
        .setBillingAddress(billingAddress)
        .selectCashOnDelivery();
    OrderPage orderPage = checkoutPage.placeOrder();

    // Then
    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );
  }
}
