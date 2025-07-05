@Regression
Feature: As a user, I want to have the possibility to see available tables so I could make a reservation. And as a Customer I should be able to make table reservations so I could visit the Restaurant on a preferred time slots.
  If the Customer is not logged into application and tries to access the reservation form, then the application should not display the form and should prompt the Customer to log in or sign up.

  Background:
    Given a user is not logged in
    And the booking page is loaded

  Scenario Outline: Accessing reservation form as a guest user
    When address, future date, time and "<guest number>" are selected
    And a slot is selected and confirmed
    Then the application should prompt the customer to log in or sign up
    Examples:
      | guest number |
      | 3            |