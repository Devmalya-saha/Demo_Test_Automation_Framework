@SignIn @Regression
Feature: As a user, I want to be able to login to access my account and interact with the application.
         US_2_USER_LOGIN

  Background:
    Given a user has signed up with valid credentials
  @Smoke
  Scenario: Successful Sign In
    When the user signs in with the valid credentials
    Then the response status code should be 200
    And the response body should have role as customer or waiter
    And the response body should have username same as the signed up user

  Scenario Outline: Sign in with the invalid credentials
    When the user signs in with email <username> and password <password>
    Then the response status code should be <statusCode>
    And the response body will display as <errorMessage>
    Examples:
      | username           | password             | statusCode | errorMessage                                 |
      | ""                 | ""                   | 400        | "email is not valid" |
      | "invalidUser"      | "wrongPass"          | 400        | "email is not valid"         |
      | "user@example.com" | "' OR '1'='1"        | 403        | "Invalid email or password"                             |
      | "user@example.com" | "short"              | 403        | "Invalid email or password"                             |
      | "valid"            | "NegativePasswor12@" | 400       | "Error while signing"                  |

  Scenario: Sign in without email field
    When the user attempts sign-in without email field
    Then the response status code should be 400
    And the response body will display as "email is not valid"

  Scenario: Sign in without password field
    When the user attempts sign-in without password field
    Then the response status code should be 400
    And the response body will display as "Password is empty!"
