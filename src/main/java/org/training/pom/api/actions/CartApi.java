package org.training.pom.api.actions;

import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import java.util.HashMap;
import java.util.Map;
import org.training.pom.api.RestAssuredClient;
import org.training.pom.data.Product;
import org.training.pom.utils.config.ConfigLoader;

public class CartApi {

  private final static String ENDPOINT_URL = "/?wc-ajax=add_to_cart";

  private final RestAssuredClient restAssuredClient =
      new RestAssuredClient(ConfigLoader.getInstance().getBaseUrl());
  private Cookies cookies;

  public CartApi() {
    this.cookies = new Cookies();
  }

  public CartApi(Cookies cookies) {
    this.cookies = cookies;
  }

  @Step("Add products to cart API, product - {product.name}, quantity - {quantity}")
  public void addToCart(Product product, int quantity) {
    Map<String, Object> formParams = new HashMap<>();
    formParams.put("product_sku", "");
    formParams.put("product_id", product.getId());
    formParams.put("quantity", quantity);

    restAssuredClient.postForm(ENDPOINT_URL, formParams, cookies);
    this.cookies = restAssuredClient.getCookies();
  }

  public Cookies getCookies() {
    return cookies;
  }
}
