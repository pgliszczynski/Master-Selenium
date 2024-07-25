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
import org.training.pom.data.BillingAddress;
import org.training.pom.utils.config.ConfigLoader;

public class BillingApi {

  private final static String ENDPOINT_URL = "/account/edit-address/billing/";

  private final RestAssuredClient restAssuredClient =
      new RestAssuredClient(ConfigLoader.getInstance().getBaseUrl());
  private final Cookies cookies;

  public BillingApi(Cookies cookies) {
    this.cookies = cookies;
  }

  @Step("Fetch Billing Address API Nonce")
  private String fetchBillingNonce() {
    Response response = getBillingAddress();
    Document document = Jsoup.parse(response.body().prettyPrint());
    Optional<Element> elementOptional = Optional.ofNullable(
        document.getElementById("woocommerce-edit-address-nonce"));

    return elementOptional.orElseThrow(() -> new RuntimeException("Element not found")).val();
  }

  private Response getBillingAddress() {
    return restAssuredClient.get(ENDPOINT_URL, cookies);
  }

  @Step("Set Billing Address")
  public void setBillingAddress(BillingAddress billingAddress) {
    Map<String, Object> formParams = new HashMap<>();
    formParams.put("billing_first_name", billingAddress.getFirstName());
    formParams.put("billing_last_name", billingAddress.getLastName());
    formParams.put("billing_company", "");
    String country = switch (billingAddress.getCountry()) {
      case "United States (US)" -> "US";
      case "India" -> "IN";
      case "United Kingdom (UK)" -> "GB";
      default -> "TT";
    };
    formParams.put("billing_country", country);
    formParams.put("billing_address_1", billingAddress.getAddress1());
    formParams.put("billing_address_2", "");
    formParams.put("billing_city", billingAddress.getCity());

    String state = switch (billingAddress.getState()) {
      case "Maharashtra" -> "MH";
      case "California" -> "CA";
      default -> billingAddress.getState();
    };
    formParams.put("billing_state", state);
    formParams.put("billing_postcode", billingAddress.getPostcode());
    formParams.put("billing_phone", "");
    formParams.put("billing_email", billingAddress.getEmail());
    formParams.put("save_address", "Save address");
    formParams.put("woocommerce-edit-address-nonce", fetchBillingNonce());
    formParams.put("_wp_http_referer", "/account/edit-address/billing/");
    formParams.put("action", "edit_address");

    restAssuredClient.postForm(ENDPOINT_URL, formParams, cookies);
  }
}
