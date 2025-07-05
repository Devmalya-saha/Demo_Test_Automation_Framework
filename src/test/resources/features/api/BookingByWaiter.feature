@BookingByWaiter @Regression
Feature:As a Waiter, I want to be able to manage reservations so I could control workload of the Restaurant.
  US_7_RESERVATION_OF_THE_TABLE_BY_WAITER

  Background: A user should be signed in
    Given the waiter is logged in

  @Smoke
  Scenario: Successful booking of a table for a particular time slot by a waiter
    When the waiter sends a POST request to the end point for booking a table "table-203" for "2" people from "17:30" to "19:00" at location "location-002" and for customer "DAVID JOHN"
    Then the response status-code must be 200
    And the response body should have status "Reserved"


  Scenario: Verify unsuccessful booking of a table once a table is booked by one waiter it cant be booked by another waiter
    Given another waiter signs in and send a POST request to the end point for booking the table "table-203" for "1" people from "17:30" to "19:00" at location "location-002" for customer "JOHNYY DEPP"
    Then the response status-code must be 409
    And the response body should have message "table already booked!"

  Scenario:Verify unsuccessful booking of a table by waiter assigned to another location
    When the waiter sends a POST request to the end point for booking a table "table-304" for "5" people from "17:30" to "19:00" at location "location-003" and for customer "Raghav Kumar"
    Then the response status-code must be 401
    And the response body should have message "you are not authorised to do this reservation"

  Scenario: Verify unsuccessful booking of a table  by waiter for a past date
    When the waiter sends a POST request to the end point for booking a table "table-204" for "5" people from "17:30" to "19:00" at location "location-002" but for a past-date
    Then the response status-code must be 400
    And the response body should have message "Can't book in the past date"


  Scenario Outline: Verify unsuccessful booking of a table for an invalid table id
    When the waiter sends a POST request to the end point for booking a table "<tableId>" for "5" people from "<timeFrom>" to "19:00" at location "location-002" and for customer "David Jack"
    Then the response status code must be 400
    Examples:
      | tableId   |
      | table-999 |
      | null      |
      |           |

  Scenario Outline:Verify unsuccessful booking of a table to for an invalid location
    When the waiter sends a POST request to the end point for booking a table "table-204" for "5" people from "<timeFrom>" to "19:00" at location "<locationId>" and for customer "David Jack"
    Then the response status code must be 400
    And the response message should be "locationId doesn't exists!"
    Examples:
      | locationId   |
      | location-005 |
      | null         |
      |              |

  Scenario Outline: Verify unsuccessful booking of a table by waiter for invalid number of guest
    When the waiter sends a POST request to the end point for booking a table "table-204" for "<numberOfGuest>" people from "17:30" to "19:00" at location "location-002" and for customer "David Jack"
    Then the response status-code must be 400
    And the response body should have message "Guest numbers must be greater than 0"
    Examples:
      | numberOfGuest |
      | 0             |
      | -1            |
      |               |
      | null          |

  Scenario Outline:Verify unsuccessful creation of a booking by waiter due to time from thats not valid
    When the waiter sends a POST request to the end point for booking a table "table-204" for "5" people from "<timeFrom>" to "19:00" at location "location-002" and for customer "David Jack"
    Then the response status-code must be 400
    And the response body should have message "Wrong time slot selected"
    Examples:
      | timeFrom |
      | null     |
      |          |
      | 17:45    |

  Scenario Outline:Verify unsuccessful creation of a booking by waiter due to time to thats not valid
    When the waiter sends a POST request to the end point for booking a table "table-204" for "5" people from "17:30" to "<timeTo>" at location "location-002" and for customer "David Jack"
    Then the response status-code must be 400
    And the response body should have message "Wrong time slot selected"
    Examples:
      | timeTo |
      | null   |
      |        |
      | 19:30  |

  Scenario: Verify if a table that is once booked is no more shown in the available slots of the location for that date
    When the waiter sends a POST request to the end point for booking a table "table-204" for "5" people from "12:15" to "13:45" at location "location-002" and for customer "David Jack"
    Then the response status-code must be 200
    And then waiter send a GET request to the bookings table end point for that "location-002" and for number of guests "5"
    Then validate if the slot for "table-104" from "12:15" to "13:45" is present or not






