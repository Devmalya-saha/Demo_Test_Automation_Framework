@BookingByCustomer @Regression @Now
Feature: As a Customer I should be able to make table reservations so I could visit the Restaurant on a preferred time slots.
  US_6_RESERVATION_OF_THE_TABLE_BY_THE_CUSTOMER

  Background: A user should be signed in
    Given the user logged in

  @Smoke
  Scenario: Successful booking of a table for a particular time slot by the user
    When the user sends a POST request to the end point for booking a table "table-204" for "5" people from "17:30" to "19:00" at location "location-002"
    Then the response status code must be 200
    And the response body should not be empty and status should be "Reserved"


  Scenario: Verify unsuccessful booking of a table once a table is booked by another user
    Given another user signs in and send a POST request to the end point for booking the table "table-204" for "5" people from "17:30" to "19:00" at location "location-002"
    Then the response status code must be 409
    And the response message should be "Table already Reserved!"

  Scenario Outline: Verify unsuccessful booking of a table for an invalid table id
    When the user sends a POST request to the end point for booking a table "<tableId>" for "5" people from "17:30" to "19:00" at location "location-002"
    Then the response status code must be 400
    Examples:
      | tableId   |
      | table-999 |
      | null      |
      |           |

  Scenario Outline:Verify unsuccessful booking of a table to for an invalid location
    When the user sends a POST request to the end point for booking a table "table-204" for "5" people from "17:30" to "19:00" at location "<locationId>"
    Then the response status code must be 400
    And the response message should be "locationId doesn't exist!"
    Examples:
      | locationId   |
      | location-005 |
      | null         |
      |              |

  Scenario: Verify unsuccessful booking of a table for a past date
    When the user sends a POST request to the end point for booking a table "table-204" for "5" people from "17:30" to "19:00" at location "location-002" but for a pastdate
    Then the response status code must be 400
    And the response message should be "Can't book in the past date"

  Scenario Outline: Verify unsuccessful creation of booking due to invalid number of guest
    When the user sends a POST request to the end point for booking a table "table-204" for "<numberOfGuest>" people from "17:30" to "19:00" at location "location-002"
    Then the response status code must be 400
    And the response message should be "Guest numbers must be greater than 0"
    Examples:
      | numberOfGuest |
      | 0             |
      | -1            |
      |               |
      | null          |

  Scenario Outline:Verify unsuccessful creation of a booking due to time from that is not valid
    When the user sends a POST request to the end point for booking a table "table-204" for "5" people from "<timeFrom>" to "20:OO" at location "location-002"
    Then the response status code must be 400
    And the response message should be "Wrong time slot selected"
    Examples:
      | timeFrom |
      | 00:00    |
      |          |
      | null     |

  Scenario Outline:Verify unsuccessful creation of a booking due to time to that is not valid
    When the user sends a POST request to the end point for booking a table "table-204" for "5" people from "17:00" to "<timeTo>" at location "location-002"
    Then the response status code must be 400
    And the response message should be "Wrong time slot selected"
    Examples:
      | timeTo |
      | 00:00  |
      |        |
      | null   |


  Scenario: Verify if a table that is once booked is no more shown in the available slots of the location for that date
    When the user sends a POST request to the end point for booking a table "table-204" for "5" people from "12:15" to "13:45" at location "location-002"
    Then the response status code must be 200
    And then send a GET request to the bookings table end point for that "location-002" and for number of guests "5"
    Then check if the slot for "table-204" for "12:15" to "13:35" is present or not






