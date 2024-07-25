package org.training.pom.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import org.training.pom.page.HomePage;
import org.training.pom.page.ProductPage;
import org.training.pom.page.StorePage;
import org.training.pom.test.base.BaseTest;

@Epic("Standalone Tests")
@Feature("Navigation tests")
public class NavigationTest extends BaseTest {

  @Description("""
      While on Home Page user navigates to Store Page using Main Menu buttons.
      """)
  @Story("Navigate to Store")
  @Test(description = "Should be able to navigate to store using Main Menu")
  public void navigateToStoreFromTheMainMenu() {
    // Given
    HomePage homePage = new HomePage(getDriver()).load();

    // When
    StorePage storePage = homePage.getStoreHeader().navigateToStoreUsingMenu();

    // Then
    assertTrue(storePage.isLoaded());
    assertEquals(storePage.getTitle(), "Store");
  }

  @Description("""
      While on Store Page user navigates to Product Page using Product Thumbnail link.
      """)
  @Story("Navigate to Product Page")
  @Test(description = "Should be able to navigate to Product Page from Store")
  void navigateFromStoreToProductPage() throws InterruptedException {
    // Given
    StorePage storePage = new StorePage(getDriver()).load();

    // When
    ProductPage productPage = storePage.clickRandomProduct();

    // Then
    assertTrue(productPage.isLoaded());
  }

  @Description("""
      While on Home Page user navigates to Featured Product Page using Featured Product thumbnail.
      """)
  @Story("Navigate to Product Page")
  @Test(description = "Should be able to navigate to Featured Product Page from Home Page")
  void navigateFromHomeToFeatureProductPage() {
    // Given
    HomePage homePage = new HomePage(getDriver()).load();

    // When
    ProductPage productPage = homePage.clickRandomFeaturedProduct();

    // Then
    assertTrue(productPage.isLoaded());
  }
}
