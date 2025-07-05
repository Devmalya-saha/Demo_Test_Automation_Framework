@Now @Regression
Feature:As a user, I want to have the possibility to see available tables so I could make a reservation.
        US_5_View Available Tables for reservation

  @Smoke
  Scenario: Get the slots available on the basis of time
    When a GET request is sent to the end point with location "location-001", time "12:00", guest number "4" and date query params
    Then response should have a status-code 200
    And response body should filter tables according to query guest number "4"
    Then store the maximum capacity from the response


  Scenario: Get an error for guests exceeding capacity
    When a GET request is sent to the end point with location "location-001", time "12:00", guest number exceeding maximum capacity and date query params
    Then response should have a status-code 404
    And contains a message "Sorry, no slots available"

  Scenario: Get error if the user is trying to find tables for past dates
    When a GET request is sent to the end point with location "location-001", time "12:00", guest number "4" and a past date
    Then response should have a status-code 400
    And contains a message "Sorry, can't travel to the past!"


  Scenario: Get error if the user is trying to find tables but some value is not present or null
    When a GET request is sent to the end point with location "location-001", time "12:00", guest number "" and date query params
    Then response should have a status-code 400
