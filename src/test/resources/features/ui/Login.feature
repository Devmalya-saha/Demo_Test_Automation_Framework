@Regression
Feature: Test user login
  US_2_User Login (R08)
  As a user, I want to be able to login to access my account and interact with the application.

  Background:
    Given the login page is loaded and the user has already registered
  @Smoke
  Scenario: Check login of valid users
    When the user logs in with correct email and password
    Then the user name will be visible on clicking the profile icon

  Scenario: Verify unsuccessful login of the user due to incorrect e-mail
    When the user tries to log in but gives incorrect email
    Then the user will remain on the login page even after clicking the submit button

  Scenario: Verify unsuccessful login of the user due to incorrect password
    When the user tries to log in but gives incorrect password
    Then the user will remain on the login page even after clicking the submit button

  Scenario: Verify unsuccessful login of the user due to missing e-mail
    When the user tries to log in but forgets to give email
    Then the proper error message showing missing email should be visible

  Scenario: Verify unsuccessful login of the user due to missing password
    When the user tries to log in but forgets to give password
    Then the proper error message showing missing password should be visible