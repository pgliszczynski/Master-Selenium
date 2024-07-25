package org.training.pom.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = BillingAddress.BillingAddressBuilder.class)
public class BillingAddress {

  private final String firstName;
  private final String lastName;
  private final String address1;
  private final String country;
  private final String city;
  private final String postcode;
  private final String state;
  private final String email;

  public BillingAddress(String firstName, String lastName, String address1, String country,
      String city,
      String postcode, String state, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address1 = address1;
    this.country = country;
    this.city = city;
    this.postcode = postcode;
    this.state = state;
    this.email = email;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class BillingAddressBuilder {

    private String firstName;
    private String lastName;
    private String address1;
    private String city;
    private String postcode;
    private String email;
    private String country;
    private String state;

    public BillingAddressBuilder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public BillingAddressBuilder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public BillingAddressBuilder address1(String address1) {
      this.address1 = address1;
      return this;
    }

    public BillingAddressBuilder country(String country) {
      this.country = country;
      return this;
    }

    public BillingAddressBuilder city(String city) {
      this.city = city;
      return this;
    }

    public BillingAddressBuilder postcode(String postcode) {
      this.postcode = postcode;
      return this;
    }

    public BillingAddressBuilder state(String state) {
      this.state = state;
      return this;
    }

    public BillingAddressBuilder email(String email) {
      this.email = email;
      return this;
    }

    public BillingAddress build() {
      return new BillingAddress(firstName, lastName, address1, country, city, postcode, state,
          email);
    }
  }

  public String getCountry() {
    return country;
  }

  public String getState() {
    return state;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress1() {
    return address1;
  }

  public String getCity() {
    return city;
  }

  public String getPostcode() {
    return postcode;
  }

  public String getEmail() {
    return email;
  }
}
