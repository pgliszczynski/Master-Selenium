package org.training.pom.api.actions;

import io.qameta.allure.Step;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.training.pom.api.RestAssuredClient;
import org.training.pom.data.User;
import org.training.pom.utils.config.ConfigLoader;

public class SignupApi {

  private final static String ENDPOINT_URL = "/account";

  private final RestAssuredClient restAssuredClient =
      new RestAssuredClient(ConfigLoader.getInstance().getBaseUrl());
  private Cookies cookies = new Cookies();

  @Step("Register user: {user.username}")
  public void register(User user) {
    Map<String, Object> formParams = new HashMap<>();
    formParams.put("username", user.getUsername());
    formParams.put("email", user.getEmail());
    formParams.put("password", user.getPassword());
    formParams.put("woocommerce-register-nonce", fetchRegisterNonceValue());
    formParams.put("register", "Register");

    restAssuredClient.postForm(ENDPOINT_URL, formParams);
    this.cookies = restAssuredClient.getCookies();
  }

  @Step("Get Register API Nonce")
  private String fetchRegisterNonceValue() {
    Response response = getAccount();
    Document document = Jsoup.parse(response.body().prettyPrint());
    Optional<Element> elementOptional = Optional.ofNullable(
        document.getElementById("woocommerce-register-nonce"));

    return elementOptional.orElseThrow(() -> new RuntimeException("Element not found")).val();
  }

  private String fetchRegisterNonceValueGroovy() {
    Response response = getAccount();
    return response.htmlPath()
                   .getString("**.findAll { it.@name == 'woocommerce-register-nonce' }.@value");
  }

  private Response getAccount() {
    return restAssuredClient.get(ENDPOINT_URL);
  }

  public Cookies getCookies() {
    return cookies;
  }
}
