package org.training.pom.data.providers;

import java.io.IOException;
import java.util.Arrays;
import org.testng.annotations.DataProvider;
import org.training.pom.data.BillingAddress;
import org.training.pom.data.Product;
import org.training.pom.utils.JsonUtils;

public class MyDataProvider {


  @DataProvider(name = "getFeaturedProducts", parallel = false)
  public Product[] getFeaturedProducts() throws IOException {
    Product[] allProducts = JsonUtils.deserializeJson("products.json", Product[].class);
    return Arrays.stream(allProducts)
                 .filter(Product::isFeatured)
                 .toArray(Product[]::new);
  }

  @DataProvider(name = "getBillingAddresses", parallel = false)
  public BillingAddress[] getBillingAddresses() throws IOException {
    return JsonUtils.deserializeJson("my-billing-address.json", BillingAddress[].class);
  }
}
