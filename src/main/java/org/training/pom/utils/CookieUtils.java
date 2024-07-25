package org.training.pom.utils;

import io.restassured.http.Cookies;
import java.util.List;
import org.openqa.selenium.Cookie;

public class CookieUtils {

  private CookieUtils() {
  }

  public static List<Cookie> mapToSeleniumCookies(Cookies cookies) {
    List<io.restassured.http.Cookie> restAssuredCookies = cookies.asList();

    return restAssuredCookies.stream()
                             .map(cookie -> new Cookie(
                                 cookie.getName(), cookie.getValue(), cookie.getDomain(),
                                 cookie.getPath(), cookie.getExpiryDate(), cookie.isSecured(),
                                 cookie.isHttpOnly(), cookie.getSameSite()))
                             .toList();
  }
}
