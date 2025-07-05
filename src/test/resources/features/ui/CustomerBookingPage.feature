@Regression
Feature: As a user, I want to have the possibility to see available tables so I could make a reservation. And as a Customer I should be able to make table reservations so I could visit the Restaurant on a preferred time slots.
  US_5_View Available Tables for reservation (R08)
  As a user, I want to have the possibility to see available tables so I could make a reservation.
  US_6_Reservation of the table by Customer (R08)
  As a Customer I should be able to make table reservations so I could visit the Restaurant on a preferred time slots.

  Background:
    Given user is logged in as customer
    And the booking page is loaded

  Scenario Outline: Check tables are being displayed for a particular guest number
    When address, future date, time and "<guest number>" are selected
    Then tables should be viewable
    Examples:
      | guest number |
      | 3            |
      | 4            |
      | 5            |


  Scenario Outline: Check customer can make valid booking and delete it as well
    When address, future date, time and "<guest number>" are selected
    And a slot is selected and confirmed
    Then a reservation confirmed message should be displayed
    And "<guest number>", future date and time should match
    Examples:
      | guest number |
      | 3            |

    @Now
    Scenario Outline: Preventing overbooking
    When "<address>", future date, time and "<guest number>" are selected
    And a slot is selected and confirmed
    Then a reservation confirmed message should be displayed
    And the same slot should not be available for "<address>" and "<guest number>"
    Examples:
        | address             | guest number |
        | 41 University Drive | 8            |


    Scenario: Access to modify or cancel reservations
    When the user navigates to reservation page
    Then the user should see an option to edit their reservation
    And the user should see an option to cancel their reservation
    And the user should be able to cancel their reservation