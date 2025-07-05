@Dishes @Now @Regression
Feature:As a Customer, I want to be able to browse the food menu through the application so that I can view available dishes and their details.
  US_9_BROWSING_THE_MENU_BY_THE_CUSTOMER

  @Smoke
  Scenario: Successfully fetch popular dishes
    When I send a GET request to the end point
    Then the response from the system status code should be 200
    And the response body should not be empty
    And all the objects should contain the required field

  Scenario Outline:Successfully return list of dishes sorted by popularity
    When the user send a GET request to the end point with dishType <dishType> and sort by popularity in <order>
    Then the status code should be 200 or 204
    And the response body should have all the parameters
    Examples:
      | dishType       | order  |
      | "Appetizers"   | "asc"  |
      | "Appetizers"   | "desc" |
      | "Main Courses" | "asc"  |
      | "Main Courses" | "desc" |
      | "Desserts"     | "asc"  |
      | "Desserts"     | "desc" |


  Scenario Outline:Successfully return list of dishes sorted by price
    When the user send a GET request to the end point with dishType <dishType> and sort by price in <order>
    Then the status code should be 200 or 204
    And the response body should have all the parameters
    And check if the dishes are shown in correct order <order> of price
    Examples:
      | dishType       | order  |
      | "Appetizers"   | "asc"  |
      | "Appetizers"   | "desc" |
      | "Main Courses" | "asc"  |
      | "Main Courses" | "desc" |
      | "Desserts"     | "asc"  |
      | "Desserts"     | "desc" |

  Scenario: Successfully fetch the particular dish by their id
    When the user sends a GET request to the end point with a id of a dish that are there
    Then  also validate the response code is 200 for all the get request






