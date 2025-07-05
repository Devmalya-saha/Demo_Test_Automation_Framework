@Regression
Feature: As a User, I want to be able to view Restaurant location details so I could learn about the Restaurant and check its ratings
         US_4_VIEW_RESTAURANT_LOCATION_DETAILS
  @Smoke
  Scenario: Successfully fetch the list of all the locations of the restaurant
    When I send a GET request to the endpoint
    Then response should have a status code 200
    And the response should return an non empty list of all the locations
    And the all the objects should be non null and have values


  Scenario Outline: Successfully fetch the popular dish of the restaurant in each of the locations
    When I fetch the speciality dishes for each <location>
    Then the response status code should be 200 for each and they should have a non null object body
    Examples:
      | location       |
      | "location-001" |
      | "location-002" |
      | "location-003" |


  Scenario: Successfully fetch a list or short location details used in drop down
    When I send a GET request to the end point for select-options
    Then the response should be status code 200 and it should not give an empty list
    And all the objects key value pair should be non null


  Scenario Outline: Successfully get all the feedbacks of a location in the order mentioned by a user by ratings
    When the user sends a GET request to the end point for "location-001" with "<type>" and sort by "<sort>"
    Then the status code should be 200
    And all the objects key value pair should be non null
    And the ratings should be in proper order "<sort>"
    Examples:
      | type    | sort |
      | Cuisine | asc  |
      | Cuisine | desc |
      | Service | asc  |
      | Service | desc |

  Scenario Outline: Successfully get all the feedbacks of a location in the order mentioned by a user by date
    When the user sends a GET request to the end point for "location-001" with "<type>" and sort by date "<sort>"
    Then the status code should be 200
    And all the objects key value pair should be non null
    #And the date should be in proper order "<sort>"
    Examples:
      | type    | sort |
      | Cuisine | asc  |
      | Cuisine | desc |
      | Service | asc  |
      | Service | desc |