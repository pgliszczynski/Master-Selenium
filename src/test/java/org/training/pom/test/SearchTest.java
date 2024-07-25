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
import org.training.pom.page.ProductPage;
import org.training.pom.page.StorePage;
import org.training.pom.test.base.BaseTest;

@Epic("Standalone Tests")
@Feature("Search tests")
public class SearchTest extends BaseTest {

  @Description("""
      User searches for partial match "Blue" - the "Blue Shoes" should be visible on the page
      """)
  @Story("Partial search")
  @Test(description = "Should search using partial match")
  void searchWithPartialMatch() {
    // Given
    StorePage storePage = new StorePage(getDriver()).load();
    String searchTerm = "Blue";

    // When
    storePage.search(searchTerm);

    // Then
    assertTrue(storePage.isSearchLoaded(searchTerm));
    assertEquals(
        storePage.getTitle(),
        "Search results: “Blue”"
    );
  }

  @Description("""
      User searches for exact match "Blue Shoes" - the Blue Shoes Product Page should be loaded
      """)
  @Story("Exact Match")
  @Test(description = "Should navigate to Product Page when exact match search used")
  void searchWithExactMatch() throws IOException {
    //Given
    Product product = new Product(1215);
    StorePage storePage = new StorePage(getDriver()).load();

    // When
    ProductPage productPage = storePage.search(product);

    // Then
    assertTrue(productPage.isLoaded());
  }

  @Description("""
      User searches for partial match "Test" that doesn't exist on the page - no products should be returned
      """)
  @Story("Negative search")
  @Test(description = "Should return no results when non-existing term searched")
  void searchWithNoResults() {
    // Given
    StorePage storePage = new StorePage(getDriver()).load();
    String searchTerm = "Test";

    // When
    storePage.search(searchTerm);

    // Then
    assertTrue(storePage.isSearchLoaded(searchTerm));
    assertEquals(
        storePage.getTitle(),
        "Search results: “" + searchTerm + "”"
    );
    assertTrue(storePage.isNoProductMessage());
  }
}
