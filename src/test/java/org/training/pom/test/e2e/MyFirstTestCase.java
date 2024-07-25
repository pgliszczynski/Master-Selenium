package org.training.pom.test.e2e;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import java.io.IOException;
import org.testng.annotations.Test;
import org.training.pom.data.BillingAddress;
import org.training.pom.data.Product;
import org.training.pom.data.User;
import org.training.pom.page.CartPage;
import org.training.pom.page.CheckoutPage;
import org.training.pom.page.HomePage;
import org.training.pom.page.OrderPage;
import org.training.pom.page.StorePage;
import org.training.pom.test.base.BaseTest;
import org.training.pom.utils.JsonUtils;

@Epic("E2E Tests")
@Feature("My first test case")
public class MyFirstTestCase extends BaseTest {

  @Link("https://example.org")
  @Link(name = "allure", type = "mylink")
  @TmsLink("12345")
  @Issue("6789")
  @Description("""
      User enters on Home Page then proceeds to enter Store
      User searches for term "Blue"
      After search is completed correctly user adds the searched product to cart
      Then navigates to Cart
      From Cart user navigates to Checkout, enters billing information and proceeds to order
      """)
  @Story("E2E - Guest checkout using Direct Bank Transfer")
  @Test(description = "Should be able to make an e2e checkout as a guest")
  void guestCheckoutUsingDirectBankTransfer() throws IOException {
    HomePage homePage = new HomePage(getDriver()).load();
    StorePage storePage = homePage.getStoreHeader().navigateToStoreUsingMenu();
    assertTrue(storePage.isLoaded());
    storePage.search("Blue");
    assertEquals(
        storePage.getTitle(),
        "Search results: “Blue”"
    );

    Product product = new Product(1215);
    storePage.getProductThumbnail().clickAddToCartButton(product.getName());
    CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
    assertTrue(cartPage.isLoaded());
    assertEquals(cartPage.getProductName(), product.getName());

    BillingAddress[] billingAddress =
        JsonUtils.deserializeJson("my-billing-address.json", BillingAddress[].class);
    CheckoutPage checkoutPage = cartPage.checkout();

    checkoutPage
        .setBillingAddress(billingAddress[0])
        .selectDirectBankTransfer();
    OrderPage orderPage = checkoutPage.placeOrder();

    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );
  }

  @Description("""
      User enters on Home Page then proceeds to enter Store
      User searches for term "Blue"
      After search is completed correctly user adds the searched product to cart
      Then navigates to Cart
      From Cart user navigates to Checkout
      On the checkout page user first logs in and then enters billing information and proceeds to order
      """)
  @Story("E2E - Logged in user checkout using Direct Bank Transfer")
  @Test(description = "Should be able to make an e2e checkout as a login user")
  void loginAndCheckoutUsingDirectBankTransfer() throws IOException {
    HomePage homePage = new HomePage(getDriver()).load();
    StorePage storePage = homePage.getStoreHeader().navigateToStoreUsingMenu();
    storePage.search("Blue");
    assertEquals(
        storePage.getTitle(),
        "Search results: “Blue”"
    );

    Product product = new Product(1215);
    storePage.getProductThumbnail().clickAddToCartButton(product.getName());
    CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
    assertEquals(cartPage.getProductName(), product.getName());

    CheckoutPage checkoutPage = cartPage.checkout();

    User user = JsonUtils.deserializeJson("user.json", User.class);
    BillingAddress[] billingAddress =
        JsonUtils.deserializeJson("my-billing-address.json", BillingAddress[].class);
    checkoutPage.showLoginForm()
                .enterUsername(user.getUsername())
                .enterPassword(user.getPassword())
                .login()
                .setBillingAddress(billingAddress[0])
                .selectDirectBankTransfer();
    OrderPage orderPage = checkoutPage.placeOrder();

    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );
  }
}
