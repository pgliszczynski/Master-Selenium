package org.training.pom.api;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import java.util.Map;

public interface ApiClient {

  Response get(String url);

  Response get(String url, Cookies cookies);

  void postForm(String url, Map<String, Object> formData);

  void postForm(String url, Map<String, Object> formData, Cookies cookies);
}
