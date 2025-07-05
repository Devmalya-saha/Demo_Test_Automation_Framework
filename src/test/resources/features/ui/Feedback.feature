@Regression
Feature: Leaving feedback for service and cuisine by Customer
    US_15

  Background: A user is logged in
    Given user is logged in as customer

    #Scenario will not run parallel because both examples end up accessing the same reservation for giving feedback
  Scenario Outline: Provide service feedback when reservation is "finished"
    Given the customer has a reservation marked as "finished"
    Then the customer should see an option to provide feedback for "<type-of-feedback>"
    And the feedback should include a star rating
    And the feedback should optionally include a textual comment
    And the customer should be able to submit the "<type-of-feedback>" feedback
    Examples:
    |type-of-feedback |
    | Service |
    | Cuisine |

    @Smoke
  Scenario: Update/view feedback for finished reservation
    Given the customer has a reservation marked as "finished"
    And the customer has already given the feedback
    Then the customer should be able to view their existing feedback
    And the customer should be able to submit the updated feedback


  Scenario: Validation of feedback submission
    Given a customer is providing feedback
    When the customer enters textual comment without star rating
    Then the submit button should be disabled

  Scenario Outline: Edge case - Feedback for canceled or future reservation
    Given the customer has a reservation marked as "<type-of-reservation>"
    Then the system should not allow feedback submission for "<type-of-reservation>" reservation
    Examples:
    |type-of-reservation |
    | cancelled |
    | future |