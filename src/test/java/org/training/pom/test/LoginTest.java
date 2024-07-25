package org.training.pom.test;

import static org.testng.Assert.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.io.IOException;
import org.testng.annotations.Test;
import org.training.pom.api.actions.CartApi;
import org.training.pom.api.actions.SignupApi;
import org.training.pom.data.Product;
import org.training.pom.data.User;
import org.training.pom.page.CheckoutPage;
import org.training.pom.test.base.BaseTest;
import org.training.pom.utils.faker.FakerUtils;

@Epic("Standalone Tests")
@Feature("Login tests")
public class LoginTest extends BaseTest {

  @Description("""
      User logs in using Login Form on the Checkout Page
      """)
  @Story("Positive tests")
  @Test(description = "Should be able to login during checkout")
  void loginDuringCheckout() throws InterruptedException, IOException {
    // Given
    SignupApi signupApi = new SignupApi();
    User user = new User(
        FakerUtils.generateRandomUsername(),
        FakerUtils.EASY_PASSWORD,
        FakerUtils.generateRandomEmail()
    );
    signupApi.register(user);

    Product product = new Product(1215);
    CartApi cartApi = new CartApi();
    cartApi.addToCart(product, 1);

    CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
    injectCookiesToBrowser(cartApi.getCookies());
    checkoutPage.load();

    // When
    checkoutPage.showLoginForm()
                .enterUsername(user.getUsername())
                .enterPassword(user.getPassword())
                .login();

    // Then
    assertTrue(checkoutPage.getProductName().contains(product.getName()));
  }

  @Description("""
      Logging fails if user enters wrong username/password combination. Error is visible on page.
      """)
  @Story("Negavite tests")
  @Test(description = "Should return error message when wrong username/password provided")
  void loginFails() throws IOException {
    // Given
    Product product = new Product(1215);
    CartApi cartApi = new CartApi();
    cartApi.addToCart(product, 1);

    CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
    injectCookiesToBrowser(cartApi.getCookies());
    checkoutPage.load();

    // When
    checkoutPage.showLoginForm()
                .enterUsername("WrongUser")
                .enterPassword("WrongUser")
                .login();

    // Then
    assertTrue(checkoutPage.getErrorMessage().contains("WrongUser is not registered on this site"));
  }
}
