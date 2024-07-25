package org.training.pom.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.util.Arrays;
import org.training.pom.utils.JsonUtils;

public class Product {

  private final int id;
  private final boolean isFeatured;
  private final String name;

  public Product(@JsonProperty("id") int id, @JsonProperty("featured") boolean isFeatured,
      @JsonProperty("name") String name) {
    this.id = id;
    this.isFeatured = isFeatured;
    this.name = name;
  }

  public Product(int id) throws IOException {
    Product[] products = JsonUtils.deserializeJson("products.json", Product[].class);
    Product foundProduct = Arrays.stream(products)
                                 .filter(product -> product.getId() == id)
                                 .findAny().orElse(new Product(0, false, ""));

    this.id = foundProduct.getId();
    this.isFeatured = foundProduct.isFeatured();
    this.name = foundProduct.getName();
  }

  public boolean isFeatured() {
    return isFeatured;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
