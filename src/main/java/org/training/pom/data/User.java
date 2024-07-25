package org.training.pom.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

  private final String username;
  private final String email;
  private final String password;

  public User(
      @JsonProperty("username") String username,
      @JsonProperty("password") String password,
      @JsonProperty("email") String email) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
