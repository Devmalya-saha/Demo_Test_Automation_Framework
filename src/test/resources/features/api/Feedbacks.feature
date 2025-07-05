@FeedBack @Now @Regression
Feature: As a Customer I want to have an option to leave feedback so I could express my opinion on service and culinary experience.
  US_15_LEAVING_FEEDBACK_FOR_SERVICE_AND_CUISINE_BY_CUSTOMER

  Background: A user is logged in
    Given The user logs in

  @Smoke @Ignore
  Scenario: Verify if you are successfully able to give feedback as a user for a booking thats in progress or already completed
    Given the user made a reservation for table number "table-104" for "5" guest and from time "14:00" to "15:30" at "location-001" for a present day
    When the user tries to give a cuisine rating "4" and service rating of "5"
    Then the response status-code should be 201


  Scenario: Unsuccessful post of a feedback for a booking that is in future
    Given the user has logged in and made a reservation for table number "table-104" for "5" guest and from time "14:00" to "15:30" at "location-001" for a future reservation
    When the user tries to give a cuisine rating "4" and service rating of "5"
    Then the response status-code should be 400
    And the response-body should have message "Can't provide feedback at this moment"


  Scenario: Unsuccessful post of a feedback for a booking that is done by another user
    When the user tries to give a cuisine rating "4" and service rating of "5" for a reservationID not of the user
    Then the response status-code should be 401

  Scenario Outline: Unsuccessful post of a  feedback for a booking due to invalid data
    Given the user made a reservation for table number "table-302" for "5" guest and from time "14:00" to "15:30" at "location-003" for a present day
    When the user tries to give a cuisine rating "<cuisineRating>" and service rating of "<serviceRating>"
    Then the response status-code should be 400
    Examples:
      | cuisineRating | serviceRating |
      | 0             | 0             |
      | 6             | 6             |
      | -1            | -1            |
      |               |               |
      | null          | null          |


