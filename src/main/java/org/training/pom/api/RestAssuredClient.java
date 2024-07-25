package org.training.pom.api;

import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.util.Map;
import org.training.pom.utils.config.ConfigLoader;

public class RestAssuredClient implements ApiClient {

  private final String baseUrl;
  private Cookies cookies = new Cookies();

  public RestAssuredClient(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  public Response get(String url) {
    return get(url, new Cookies());
  }

  @Override
  public Response get(String url, Cookies cookies) {
    Response response = given()
            .baseUri(baseUrl)
            .cookies(cookies)
            .filter(new AllureRestAssured())
            .log().all()
        .when()
            .get(url)
        .then()
            .log().all()
            .extract()
            .response();

    if (response.getStatusCode() != 200) {
      throw new RuntimeException(
          "Failed to fetch the account, HTTP Status Code: " + response.getStatusCode());
    }
    return response;
  }

  @Override
  public void postForm(String url, Map<String, Object> formData) {
    postForm(url, formData, new Cookies());
  }

  @Override
  public void postForm(String url, Map<String, Object> formData, Cookies cookies) {
    Header header = new Header("Content-Type", ContentType.URLENC.getAcceptHeader());
    Headers headers = new Headers(header);

    Response response = given()
            .baseUri(ConfigLoader.getInstance().getBaseUrl())
            .headers(headers)
            .formParams(formData)
            .cookies(cookies)
            .filter(new AllureRestAssured())
            .log().all()
        .when()
            .post(url)
        .then()
            .log().all()
            .extract()
            .response();

    if (response.getStatusCode() != 302 && response.getStatusCode() != 200) {
      throw new RuntimeException(
          "Failed to register the account, HTTP Status Code: " + response.getStatusCode());
    }

    this.cookies = response.getDetailedCookies();
  }

  public Cookies getCookies() {
    return cookies;
  }
}
