package org.training.pom.junit.test;

/*import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;*/

import java.io.IOException;
import org.training.pom.data.BillingAddress;
import org.training.pom.data.Product;
import org.training.pom.data.User;
import org.training.pom.junit.test.base.BaseTest;
import org.training.pom.page.CartPage;
import org.training.pom.page.CheckoutPage;
import org.training.pom.page.HomePage;
import org.training.pom.page.OrderPage;
import org.training.pom.page.StorePage;
import org.training.pom.utils.JsonUtils;

public class MyFirstTestCase extends BaseTest {

  //@Test
  void guestCheckoutUsingDirectBankTransfer() throws IOException {
    HomePage homePage = new HomePage(driver).load();
    StorePage storePage = homePage.getStoreHeader().navigateToStoreUsingMenu();
    /*    assertTrue(storePage.isLoaded());*/
    storePage.search("Blue");
/*    assertEquals(
        storePage.getTitle(),
        "Search results: “Blue”"
    );*/

    Product product = new Product(1215);
    storePage.getProductThumbnail().clickAddToCartButton(product.getName());
    CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
/*    assertTrue(cartPage.isLoaded());
    assertEquals(cartPage.getProductName(), product.getName());*/

    BillingAddress billingAddress =
        JsonUtils.deserializeJson("my-billing-address.json", BillingAddress.class);
    CheckoutPage checkoutPage = cartPage.checkout();

    checkoutPage
        .setBillingAddress(billingAddress)
        .selectDirectBankTransfer();
    OrderPage orderPage = checkoutPage.placeOrder();

/*    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );*/
  }

  //@Test
  void loginAndCheckoutUsingDirectBankTransfer() throws IOException {
    HomePage homePage = new HomePage(driver).load();
    StorePage storePage = homePage.getStoreHeader().navigateToStoreUsingMenu();
    storePage.search("Blue");
/*    assertEquals(
        storePage.getTitle(),
        "Search results: “Blue”"
    );*/

    Product product = new Product(1215);
    storePage.getProductThumbnail().clickAddToCartButton(product.getName());
    CartPage cartPage = storePage.getProductThumbnail().clickViewCart();
    /*    assertEquals(cartPage.getProductName(), product.getName());*/

    CheckoutPage checkoutPage = cartPage.checkout();

    User user = JsonUtils.deserializeJson("user.json", User.class);
    BillingAddress billingAddress =
        JsonUtils.deserializeJson("my-billing-address.json", BillingAddress.class);
    checkoutPage.showLoginForm()
                .enterUsername(user.getUsername())
                .enterPassword(user.getPassword())
                .login()
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer();
    OrderPage orderPage = checkoutPage.placeOrder();

/*    assertEquals(
        orderPage.getOrderNotice(),
        "Thank you. Your order has been received."
    );*/
  }
}
