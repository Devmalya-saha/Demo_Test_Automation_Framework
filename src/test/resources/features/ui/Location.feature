@Regression
Feature:Test if the user is able to view the restaurant details
  US_4_View Restaurant location Details (R08)
  As a User, I want to be able to view Restaurant location details so I could learn about the Restaurant and check its ratings\

  @Smoke
  Scenario: Verify if the user is able to see the location address
    Given the user is on the main page
    When the user navigates to the location page of "first location"
    Then the location address must be visible

  @Smoke
  Scenario: Verify if the user is able to see the location details
    Given the user is on the main page
    When the user navigates to the location page of "first location"
    Then the location details must be visible


  Scenario: Verify if the user is able to see the restaurant rating of not
    Given the user is on the main page
    When the user navigates to the location page of "first location"
    Then the location rating must be visible to the user


  Scenario: Verify if the user is able to interact with the table booking from location page
    Given the user is on the main page
    When the user navigates to the location page of "first location"
    Then if the user clicks on book a table button he must be able to navigate to the booking page


  Scenario: Verify if the user is able to see the speciality dishes of the restaurant
    Given the user is on the main page
    When the user navigates to the location page of "first location"
    Then the speciality dishes of the location must be visible

  Scenario: Verify if the user is able to see the service based reviews of that particular location
    Given the user is on the main page
    When the user navigates to the location page of "first location"
    Then the user must be able to see the service based review of that location

  Scenario: Verify if the user is able to see the cuisine based reviews of that particular location
    Given the user is on the main page
    When the user navigates to the location page of "first location"
    Then the user must be able to see the cuisine based review of that location


