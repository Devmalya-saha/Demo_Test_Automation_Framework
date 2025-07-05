@Regression
Feature: As a user I want to get all the information that are related to my profile
         As a User I want to update my profile information, so that my details are current and accurate.\
         US_15_USER_PROFILE_INFORMATION
  @Smoke
  Scenario: Verify if you are able to get the correct fields values
    Given the user has already registered
    When  the user sends a GET request to the end point for getting user data
    Then the response-status code should be 200
    And validate that the first name and last name are same as one sent during registering


