package org.training.pom.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.io.IOException;
import org.testng.annotations.Test;
import org.training.pom.data.Product;
import org.training.pom.data.providers.MyDataProvider;
import org.training.pom.page.CartPage;
import org.training.pom.page.HomePage;
import org.training.pom.page.ProductPage;
import org.training.pom.page.StorePage;
import org.training.pom.test.base.BaseTest;

@Epic("Standalone Tests")
@Feature("Add to cart tests")
public class AddToCartTest extends BaseTest {

  @Description("""
      While on Store Page - User adds to cart a Product
      Then navigates to Cart Page to check if the product was added
      """)
  @Story("Add to cart from Store Page")
  @Test(description = "Should be able to add product to Cart from Store Page")
  void addToCartFromStorePage() throws IOException {
    // Given
    Product product = new Product(1215);
    StorePage storePage = new StorePage(getDriver()).load();

    // When
    storePage.getProductThumbnail().clickAddToCartButton(product.getName());
    CartPage cartPage = storePage.getProductThumbnail().clickViewCart();

    // Then
    assertTrue(cartPage.isLoaded());
    assertEquals(cartPage.getProductName(), product.getName());
  }

  @Description("""
      While on Home Page - User adds to cart a Feature Product
      Then navigates to Cart Page to check if the product was added
      """)
  @Story("Add to cart Featured Product")
  @Test(description = "Should be able to add Featured Product on a Home Page to Cart")
  void addFeaturedProductToCart() throws IOException {
    // Given
    Product product = new Product(1215);
    HomePage homePage = new HomePage(getDriver()).load();

    // When
    homePage.getProductThumbnail().clickAddToCartButton(product.getName());
    CartPage cartPage = homePage.getProductThumbnail().clickViewCart();

    // Then
    assertTrue(cartPage.isLoaded());
    assertEquals(cartPage.getProductName(), product.getName());
  }

  @Description("""
      While on Store Page - User clicks on Product thumbnail to navigate to product Page
      Then adds to cart the Product
      Then navigates to Cart Page to check if the product was added
      """)
  @Story("Add to cart from Product Page")
  @Test(description = "Should be able to add to Cart from a Product Page")
  void addToCartFromProductPage() throws IOException {
    // Given
    Product product = new Product(1215);
    StorePage storePage = new StorePage(getDriver()).load();

    // When
    ProductPage productPage = storePage.clickProductLink(product.getName());
    assertTrue(productPage.isLoaded());

    productPage.clickAddToCart();
    CartPage cartPage = productPage.clickViewCart();

    // Then
    assertTrue(cartPage.isLoaded());
    assertEquals(cartPage.getProductName(), product.getName());
  }

  @Description("""
      While on Home Page - User adds to cart a Feature Product
      Then navigates to Cart Page to check if the product was added
      """)
  @Story("Add to cart Featured Product")
  @Test(dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProvider.class,
      description = "Should be able to add Feature Product ot cart from Home Page")
  void addToCartFeaturedProducts(Product featuredProduct) {
    // Given
    HomePage homePage = new HomePage(getDriver()).load();

    // When
    homePage.getProductThumbnail().clickAddToCartButton(featuredProduct.getName());
    CartPage cartPage = homePage.getProductThumbnail().clickViewCart();

    // Then
    assertTrue(cartPage.isLoaded());
    assertEquals(cartPage.getProductName(), featuredProduct.getName());
  }
}
