package org.training.pom.api.actions;

import java.io.IOException;
import org.training.pom.data.BillingAddress;
import org.training.pom.data.Product;
import org.training.pom.data.User;
import org.training.pom.data.providers.MyDataProvider;
import org.training.pom.utils.faker.FakerUtils;

public class DummyClass {

  public static void main(String[] args) throws IOException {
    Product product = new Product(1215, true, "Blue Shoes");
    User user = new User(
        FakerUtils.generateRandomUsername(),
        FakerUtils.EASY_PASSWORD,
        FakerUtils.generateRandomEmail()
    );

    SignupApi signupApi = new SignupApi();
    signupApi.register(user);
    System.out.println(signupApi.getCookies());

/*    CartApi cartApi = new CartApi();
    cartApi.addToCart(product, 1);
    System.out.println(cartApi.getCookies());

    cartApi = new CartApi(signupApi.getCookies());
    cartApi.addToCart(product, 1);
    System.out.println(cartApi.getCookies());*/

    BillingAddress[] billingAddress = new MyDataProvider().getBillingAddresses();
    BillingApi billingApi = new BillingApi(signupApi.getCookies());
    billingApi.setBillingAddress(billingAddress[2]);
  }
}
